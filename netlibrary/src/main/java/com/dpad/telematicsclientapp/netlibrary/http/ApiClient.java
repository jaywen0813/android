package com.dpad.telematicsclientapp.netlibrary.http;

import android.content.Context;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import com.dpad.telematicsclientapp.mvp.BuildConfig;
import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.netlibrary.net.NetErrorCode;
import com.socks.library.KLog;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

;

/**
 * Created by vigss on 2018/3/19.
 */

public class ApiClient {

    private static long DEFAULT_TIME_OUT = 20000;

    public static <T> T initService(String baseUrl, Class<T> clazz, SortedMap<String, String> map) {
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(httpLoggingInterceptor);
////            builder.addNetworkInterceptor(new StethoInterceptor());
//            //BuildConfig.STETHO.addNetworkInterceptor(builder);
//        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient(map))
                .build();
        return retrofit.create(clazz);
    }

    public static OkHttpClient genericClient(SortedMap<String, String> map) {
        Date date = new Date();
        String currentTimeStamp = date.getTime() + "";
//        map.put("currentTimeStamp", currentTimeStamp);
        String sign = MD5.GetMD5Code(getSecretStr(map, currentTimeStamp));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        Context activity = Kits.Empty.check(AppManager.getInstance().getCurrentActivity()) ? MainApplicaton.getInstance().getBaseContext() : AppManager.getInstance().getCurrentActivity();
        if (activity != null) {
            if (NetworkUtil.isNetworkAvailable(activity)) {
                T.showToastSafeError("当前网络不可用,请检查网络设置!");
            }
        }
        if (BuildConfig.DEBUG) {
            httpClient = new OkHttpClient.Builder().connectTimeout(20000, TimeUnit.MILLISECONDS)
                    .readTimeout(20000, TimeUnit.MILLISECONDS)
                    .addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/json;charset=UTF-8")
//                                    .addHeader("Accept-Encoding", "gzip, deflate")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Accept", "*/*")
                                    .addHeader("Cookie", "add cookies here")
                                    .addHeader("currentTimeStamp", currentTimeStamp)
                                    .addHeader("brandCode", AppConstants.BRANDCODE)
                                    .addHeader("sign", sign)
                                    .addHeader("appId", AppConstants.APPID)
                                    .addHeader("userName", MainApplicaton.LOGINRESULTVO.getUserName())
                                    .addHeader("userId", MainApplicaton.LOGINRESULTVO.getUserId())
                                    .addHeader("appVersionNo", AppConstants.APPVERSIONNO)
                                    .addHeader("deviceNo", AppConstants.getMyUUID())
                                    .addHeader("deviceType", AppConstants.DEVICETYPE)
                                    .addHeader("token", MainApplicaton.LOGINRESULTVO.getToken())
                                    .addHeader("vin", Kits.Empty.check(MainApplicaton.getVin()) ? "" : MainApplicaton.getVin())
                                    .build();
                            Response response = chain.proceed(request);
                            ResponseBody responseBody = response.body();
                            long contentLength = responseBody.contentLength();

                            if (!bodyEncoded(response.headers())) {
                                BufferedSource source = responseBody.source();
                                source.request(Long.MAX_VALUE); // Buffer the entire body.
                                Buffer buffer = source.buffer();

                                Charset charset = Charset.forName("UTF-8");
                                MediaType contentType = responseBody.contentType();
                                if (contentType != null) {
                                    try {
                                        charset = contentType.charset(Charset.forName("UTF-8"));
                                    } catch (UnsupportedCharsetException e) {
                                        KLog.e(e.toString());
                                        return response;
                                    }
                                }

                                if (!isPlaintext(buffer)) {
                                    return response;
                                }

                                if (contentLength != 0) {
                                    String result = buffer.clone().readString(charset);
                                    //得到所需的string，开始判断是否异常
                                    //***********************do something*****************************
                                    KLog.e("请求返回JSon", result);
                                    NetErrorCode.getCodeMessageShow(result);
                                }

                            }
                            return response;
                        }

                    })
                    .build();
        } else {
            httpClient = new OkHttpClient.Builder().connectTimeout(20000, TimeUnit.MILLISECONDS)
                    .readTimeout(20000, TimeUnit.MILLISECONDS)
//                    .addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/json;charset=UTF-8")
//                                    .addHeader("Accept-Encoding", "gzip, deflate")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Accept", "*/*")
                                    .addHeader("Cookie", "add cookies here")
                                    .addHeader("currentTimeStamp", currentTimeStamp)
                                    .addHeader("brandCode", AppConstants.BRANDCODE)
                                    .addHeader("sign", sign)
                                    .addHeader("appId", AppConstants.APPID)
                                    .addHeader("userName", MainApplicaton.LOGINRESULTVO.getUserName())
                                    .addHeader("userId", MainApplicaton.LOGINRESULTVO.getUserId())
                                    .addHeader("appVersionNo", AppConstants.APPVERSIONNO)
                                    .addHeader("deviceNo", AppConstants.getMyUUID())
                                    .addHeader("deviceType", AppConstants.DEVICETYPE)
                                    .addHeader("token", MainApplicaton.LOGINRESULTVO.getToken())
                                    .addHeader("vin", Kits.Empty.check(MainApplicaton.getVin()) ? "" : MainApplicaton.getVin())
                                    .build();
                            Response response = chain.proceed(request);
                            ResponseBody responseBody = response.body();
                            long contentLength = responseBody.contentLength();

                            if (!bodyEncoded(response.headers())) {
                                BufferedSource source = responseBody.source();
                                source.request(Long.MAX_VALUE); // Buffer the entire body.
                                Buffer buffer = source.buffer();

                                Charset charset = Charset.forName("UTF-8");
                                MediaType contentType = responseBody.contentType();
                                if (contentType != null) {
                                    try {
                                        charset = contentType.charset(Charset.forName("UTF-8"));
                                    } catch (UnsupportedCharsetException e) {
                                        return response;
                                    }
                                }

                                if (!isPlaintext(buffer)) {
                                    return response;
                                }

                                if (contentLength != 0) {
                                    String result = buffer.clone().readString(charset);
                                    //得到所需的string，开始判断是否异常
                                    //***********************do something*****************************
                                    NetErrorCode.getCodeMessageShow(result);
                                }

                            }
                            return response;
                        }

                    })
                    .build();
        }
        return httpClient;
    }

    public static String getSecretStr(Map<String, String> parm, String currentTimeStamp) {
        //step 1 排序
        SortedMap<Object, Object> parameters = new TreeMap<>(parm);
        parameters.put("currentTimeStamp", currentTimeStamp);
        String secretStr = "";
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v;
            if (entry.getValue() == null) {
                v = "";
            } else {
                v = entry.getValue().toString();
                KLog.e("----------", "getSecretStr: " + v);
            }

            secretStr += v;
        }
        return secretStr;
    }

    private static boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    /**
     * 动态添加header所需的retrofit
     *
     * @param baseUrl 主要的地址
     * @return
     */
    public static Retrofit getRetrofit(String baseUrl) {
        if (Kits.Empty.check(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        return retrofit;
    }

    /**
     * @return 动态添加header 所需的okHttp
     */
    private static OkHttpClient getOkHttpClient() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient;
        Context activity = Kits.Empty.check(AppManager.getInstance().getCurrentActivity()) ? MainApplicaton.getInstance().getBaseContext() : AppManager.getInstance().getCurrentActivity();
        if (activity != null) {
            if (NetworkUtil.isNetworkAvailable(activity)) {
                T.showToastSafeError("当前网络不可用,请检查网络设置!");
                KLog.e("当前网络不可用,请检查网络设置!");
            }
        }
        if (BuildConfig.DEBUG) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new Interceptor() {

                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder().build();

                            Response response = chain.proceed(request);
                            ResponseBody responseBody = response.body();
                            long contentLength = responseBody.contentLength();
                            if (!bodyEncoded(response.headers())) {
                                BufferedSource source = responseBody.source();
                                source.request(Long.MAX_VALUE);
                                Buffer buffer = source.buffer();

                                Charset charset = Charset.forName("UTF-8");
                                MediaType mediaType = responseBody.contentType();
                                if (mediaType != null) {
                                    try {
                                        charset = mediaType.charset(Charset.forName("UTF-8"));
                                    } catch (UnsupportedCharsetException e) {
                                        return response;
                                    }
                                }

                                if (!isPlaintext(buffer)) {
                                    return response;
                                }

                                if (contentLength != 0) {
                                    String result = buffer.clone().readString(charset);
                                    KLog.e("TAG", "-------" + result);
                                    NetErrorCode.getCodeMessageShow(result);
                                }

                            }
                            return response;
                        }
                    })
                    .build();
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(new Interceptor() {

                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder().build();
                            Response response = chain.proceed(request);
                            ResponseBody responseBody = response.body();
                            long contentLength = responseBody.contentLength();
                            if (!bodyEncoded(response.headers())) {
                                BufferedSource source = responseBody.source();
                                source.request(Long.MAX_VALUE);
                                Buffer buffer = source.buffer();
                                Charset charset = Charset.forName("UTF-8");
                                MediaType mediaType = responseBody.contentType();
                                if (mediaType != null) {
                                    try {
                                        charset = mediaType.charset(Charset.forName("UTF-8"));
                                    } catch (UnsupportedCharsetException e) {
                                        return response;
                                    }
                                }
                                if (!isPlaintext(buffer)) {
                                    return response;
                                }
                                if (contentLength != 0) {
                                    String result = buffer.clone().readString(charset);
                                    NetErrorCode.getCodeMessageShow(result);
                                }

                            }
                            return response;
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }

}
