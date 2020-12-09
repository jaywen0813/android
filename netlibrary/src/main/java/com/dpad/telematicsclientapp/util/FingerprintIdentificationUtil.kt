package com.dpad.crmclientapp.android.util


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.provider.Settings
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.app.FragmentActivity
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.util.Base64
import com.dpad.crmclientapp.android.R
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.fragment.FingerprintDialogForQFragment
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.fragment.FingerprintDialogFragment
import com.dpad.crmclientapp.android.modules.yckz.activity.RemoteControlActivity
import com.dpad.crmclientapp.android.util.utils.Constant
import com.dpad.crmclientapp.android.util.utils.DialogUtil
import com.dpad.crmclientapp.android.util.utils.T
import com.socks.library.KLog
import java.security.*
import java.security.spec.ECGenParameterSpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-21-0021 16:18
 * 描    述：
 * 修订历史：
 * ================================================
 */
open class FingerprintIdentificationUtil() {

    companion object {

        //指纹识别开始

        private const val DEFAULT_KEY_NAME = "default_key"

        private lateinit var keyStore: KeyStore

        /**
         * 管理系统提供的生物识别对话框的类（Android P）
         */
        private lateinit var mBiometricPrompt: BiometricPrompt


        private lateinit var mFingerprintManagerCompat: FingerprintManagerCompat

        private var mSignature: Signature? = null
        private var mToBeSignedMessage: String? = null

        /**
         * 指纹校验错误,或者校验5次未能成功
         */
        private var mFingerPrintErrorListener: FingerPrintErrorListener? = null

        private var errorCount = 0//验证错误的次数

        /**
         * 提供取消正在进行的操作的功能
         */
        private lateinit var mCancellationSignal: CancellationSignal

        /**
         * 识别回调（Android P）
         */
        private var mAuthenticationCallback: BiometricPrompt.AuthenticationCallback? = null


        //指纹识别结束fragment
        @SuppressLint("StaticFieldLeak")
        var fragment: FingerprintDialogFragment? = null

        //指纹识别结束fragment
        @SuppressLint("StaticFieldLeak")
        var fragmentQ: FingerprintDialogForQFragment? = null


        //判断试问识别的弹框是否显示,如果显示返回键监听需要处理,true是显示
        private var ishowIngFragment = false//弹出指纹框

        interface FingerPrintErrorListener {
            fun fingerSuccess()
            fun fingerFail()
            fun fingerCancel()
        }

        //指纹识别开始
        fun startInit(context: FragmentActivity, mmFingerPrintErrorListener: FingerPrintErrorListener) {
            this.mFingerPrintErrorListener = mmFingerPrintErrorListener;
            if (isSupportFingerprint(context)) {
                initFingerprint(context)
            }
        }


        /**
         * 判断是否支持指纹（先用Android M的方式来判断）
         */
        private fun isSupportFingerprint(context: FragmentActivity): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    mFingerPrintErrorListener?.fingerFail()
                    return false
                } else {
                    val keyguardManager = context.getSystemService(KeyguardManager::class.java)
                    val fingerprintManager = context.getSystemService(FingerprintManager::class.java)

                    if (!fingerprintManager.isHardwareDetected) {
                        mFingerPrintErrorListener?.fingerFail()
                        return false
                    } else if (!keyguardManager.isKeyguardSecure) {
                        showDialog(context, "该功能需要开启指纹解锁,现在进行设置?")
                        return false
                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                        showDialog(context, "您未添加指纹,前往系统添加?")
                        return false
                    }
                }
            } else {
                /*
                 * 硬件是否支持指纹识别
                 * */
                if (!FingerprintManagerCompat.from(context).isHardwareDetected) {
                    return false;
                }
                //是否已添加指纹
                if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
                    return false;
                }
            }


            return true
        }


        /**
         * 判断是否支持指纹（先用Android M的方式来判断）,不带弹窗,用于开启指纹校验
         */
        fun isSupportFingerprintWithNoDialog(context: FragmentActivity): Boolean {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    mFingerPrintErrorListener?.fingerFail()
                    return false
                } else {
                    val keyguardManager = context.getSystemService(KeyguardManager::class.java)
                    val fingerprintManager = context.getSystemService(FingerprintManager::class.java)

                    if (!fingerprintManager.isHardwareDetected) {
                        T.showToastSafeError("您的手机不支持指纹功能")
                        return false
                    } else if (!keyguardManager.isKeyguardSecure) {
                        return false
                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                        return false
                    }
                }
            } else {
                /*
                 * 硬件是否支持指纹识别
                 * */
                if (!FingerprintManagerCompat.from(context).isHardwareDetected) {
                    T.showToastSafeError("您的手机不支持指纹功能")
                    return false;
                }
                //是否已添加指纹
                if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
                    return false;
                }
            }
            return true
        }

        private fun showDialog(context: FragmentActivity, des: String) {
            val dialog = DialogUtil.showBasicDialog(context, des, "取消", "去设置", { dialog, confirm ->
                if (confirm) {
                    val intent = Intent(Settings.ACTION_SECURITY_SETTINGS);
                    context.startActivityForResult(intent, Constant.SETTING_FINGERPRINT_REQUEST);
                    dialog.dismiss()
                }
            }, R.mipmap.fingerprint_icon)
        }

        /**
         * 默认弹窗
         */
        private fun initFingerprint(context: FragmentActivity) {
            ishowIngFragment = true
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                initFingerprintManager(context)
            } else {
                errorCount = 0
                initBiometricP(context)
            }
        }

        /**
         * Android P+ 指纹
         */
        @TargetApi(Build.VERSION_CODES.P)
        private fun initBiometricP(context: FragmentActivity) {
//            mFingerprintManagerCompat = FingerprintManagerCompat.from(context)
            showFingerPrintQDialog(context)


//            mBiometricPrompt = BiometricPrompt.Builder(context)
//                    .setTitle("请验证指纹")
//                    .setDescription("")
//                    .setNegativeButton("取消",
//                            context.mainExecutor, object : DialogInterface.OnClickListener {
//                        override fun onClick(dialog: DialogInterface?, which: Int) {
//                            mFingerPrintErrorListener?.fingerCancel()
//                            T.showToastSafe("已取消指纹验证")
//                        }
//
//                    }
//                    )
//                    .build()
//
//            try {
//                val keyPair = generateKeyPair(DEFAULT_KEY_NAME, true)
//                // 将密钥对的公钥部分发送到服务器，该公钥将用于认证((本项目没跟服务器校对)
//                mToBeSignedMessage = StringBuilder()
//                        .append(Base64.encodeToString(keyPair.public.encoded, Base64.URL_SAFE))
//                        .append(":")
//                        .append(DEFAULT_KEY_NAME)
//                        .append(":")
//                        // Generated by the server to protect against replay attack
//                        .append("12345")
//                        .toString()
//
//                mSignature = initSignature(DEFAULT_KEY_NAME)
//            } catch (e: Exception) {
//                throw RuntimeException(e)
//            }
//
//            mCancellationSignal = CancellationSignal()
//            mAuthenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
//                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                    super.onAuthenticationError(errorCode, errString)
//                    mFingerPrintErrorListener?.fingerFail()
//                    KLog.i("onAuthenticationError $errString", "")
//                }
//
//
//                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                    super.onAuthenticationSucceeded(result)
//                    KLog.i("onAuthenticationSucceeded $result", "")
//                    onAuthenticated(context)
//                }
//
//                override fun onAuthenticationFailed() {
//                    super.onAuthenticationFailed()
//                    errorCount++
////                    if (errorCount == 3) {
////                        mCancellationSignal.cancel()
////                        mFingerPrintErrorListener.usePin()
////                    }
//                }
//
//            }
//
//            mBiometricPrompt.authenticate(BiometricPrompt.CryptoObject(mSignature), mCancellationSignal, context.mainExecutor, mAuthenticationCallback)
        }

        /**
         * Android M+ 指纹
         */
        @TargetApi(Build.VERSION_CODES.M)
        private fun initFingerprintManager(context: FragmentActivity) {
            initKey()
            val cipher = initCipher()
            // 指纹识别弹窗
            showFingerPrintDialog(cipher, context)
        }

        /**
         * 创建密钥
         */
        @TargetApi(Build.VERSION_CODES.M)
        private fun initKey() {
            try {
                keyStore = KeyStore.getInstance("AndroidKeyStore")
                keyStore.load(null)
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
                val builder = KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                keyGenerator.init(builder.build())
                keyGenerator.generateKey()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        /**
         * 初始化Cipher(加密类，指纹扫描器会使用这个对象来判断认证结果的合法性)
         */
        @TargetApi(Build.VERSION_CODES.M)
        private fun initCipher(): Cipher {
            try {
                val key = keyStore.getKey(DEFAULT_KEY_NAME, null) as SecretKey
                val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7)
                cipher.init(Cipher.ENCRYPT_MODE, key)
                return cipher
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        /**
         * 显示指纹识别弹窗
         */
        private fun showFingerPrintDialog(cipher: Cipher, context: FragmentActivity) {
            fragment = FingerprintDialogFragment()
            fragment?.setCipher(cipher)
            fragment?.setFingerPrintErrorListener(mFingerPrintErrorListener)
            fragment?.setOnClickListener(({ this.onAuthenticated(context) }))
            if (fragment != null) {
                context.supportFragmentManager.beginTransaction().add(fragment!!, "fingerprint").commitAllowingStateLoss();
            }
        }

        /**
         * 显示指纹识别弹窗
         */
        private fun showFingerPrintQDialog(context: FragmentActivity) {
            fragmentQ = FingerprintDialogForQFragment()
            fragmentQ?.setFingerPrintErrorListener(mFingerPrintErrorListener)
            fragmentQ?.setOnClickListener(({ this.onAuthenticated(context) }))
            if (fragmentQ != null) {
                context.supportFragmentManager.beginTransaction().add(fragmentQ!!, "fingerprint").commitAllowingStateLoss();
            }
        }

        /**
         * 认证成功
         */
        private fun onAuthenticated(context: FragmentActivity) {
            if (fragment != null) {
                fragment?.onDestroy()
            }
            if (fragmentQ != null) {
                fragmentQ?.onDestroy()
            }

            mFingerPrintErrorListener?.fingerSuccess()
        }

        /**
         * Generate NIST P-256 EC Key pair for signing and verification
         *
         * @param keyName
         * @param invalidatedByBiometricEnrollment
         * @return
         * @throws Exception
         */
        @TargetApi(Build.VERSION_CODES.P)
        @Throws(Exception::class)
        private fun generateKeyPair(keyName: String, invalidatedByBiometricEnrollment: Boolean): KeyPair {
            val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore")

            val builder = KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_SIGN)
                    .setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA384,
                            KeyProperties.DIGEST_SHA512)
                    // Require the user to authenticate with a biometric to authorize every use of the key
                    .setUserAuthenticationRequired(true)
                    .setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)

            keyPairGenerator.initialize(builder.build())

            return keyPairGenerator.generateKeyPair()
        }

        @Throws(Exception::class)
        private fun getKeyPair(keyName: String): KeyPair? {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            if (keyStore.containsAlias(keyName)) {
                // Get public key
                val publicKey = keyStore.getCertificate(keyName).publicKey
                // Get private key
                val privateKey = keyStore.getKey(keyName, null) as PrivateKey
                // Return a key pair
                return KeyPair(publicKey, privateKey)
            }
            return null
        }

        @Throws(Exception::class)
        private fun initSignature(keyName: String): Signature? {
            val keyPair = getKeyPair(keyName)

            if (keyPair != null) {
                val signature = Signature.getInstance("SHA256withECDSA")
                signature.initSign(keyPair.private)
                return signature
            }
            return null
        }
    }

}