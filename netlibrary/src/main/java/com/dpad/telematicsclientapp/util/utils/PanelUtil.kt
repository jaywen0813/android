package com.dpad.crmclientapp.android.util.utils

import com.socks.library.KLog
import java.util.*

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/12/27 14:47
 * 描    述：
 * ================================================
 */
object PanelUtil {

    private val pm = arrayListOf("0", "50", "100", "150", "200", "250")
    private val wd = arrayListOf("-30", "-15", "0", "15", "30", "45")
    private val xhlc = arrayListOf("0", "60", "120", "180", "240", "300", "360")
    private val percent = arrayListOf("0", "20", "40", "60", "80", "100")
    private val oil = arrayListOf("0", "16", "32", "48", "64", "80", "100")

    private var zcts = arrayListOf("0", "16", "32", "48", "64", "80", "100")//注册天数


    private val visitorPanelName = arrayListOf("空气质量", "今日气温", "我们已相遇", "空气湿度", "周边用户")//游客
    private val nomalPanelName = arrayListOf("空气质量", "今日气温", "我们已相遇", "空气湿度", "周边用户")//非T用户
    private val fuelPanelName = arrayListOf("空气质量", "今日气温", "剩余油量", "油耗击败", "周边用户")//燃油车
//    private val electricallyPanelName = arrayListOf("空气质量", "今日气温", "续航里程", "本周电耗击败", "周边用户")//电动车
    private val electricallyPanelName = arrayListOf("空气质量", "今日气温", "续航里程", "剩余电量", "周边用户")//电动车
    private val hybridPanelName = arrayListOf("空气质量", "今日气温", "剩余电量", "剩余油量", "油耗击败")//混合车


    private val visitorUnitName = arrayListOf("PM2.5", "℃", "Day", "%", "User")//游客
    private val nomalUnitName = arrayListOf("PM2.5", "℃", "Day", "%", "User")//非T用户
    private val fuelUnitName = arrayListOf("PM2.5", "℃", "%", "%", "User")//燃油车
    private val electricallyUnitName = arrayListOf("PM2.5", "℃", "km", "%", "User")//电动车
    private val hybridUnitName = arrayListOf("PM2.5", "℃", "%", "%", "%")//混合车


    private val cornerList = arrayListOf(225, 225, 270, 225, 215)

    /**
     * 0：游客1:电动T车2：混动T车3：燃油T车4：非T车）
     */
    fun getPanelNameList(userVehType: String): ArrayList<String> {
        when (userVehType) {
            "1" -> {
                return electricallyPanelName
            }
            "2" -> {
                return hybridPanelName
            }
            "3" -> {
                return fuelPanelName
            }
            "4" -> {
                return nomalPanelName
            }
            else -> {
                return visitorPanelName
            }

        }
    }

    /**
     * 0：游客1:电动T车2：混动T车3：燃油T车4：非T车）
     */
    fun getPanelUnitNameList(userVehType: String): ArrayList<String> {
        when (userVehType) {
            "1" -> {
                return electricallyUnitName
            }
            "2" -> {
                return hybridUnitName
            }
            "3" -> {
                return fuelUnitName
            }
            "4" -> {
                return nomalUnitName
            }
            else -> {
                return visitorUnitName
            }

        }
    }


    /**
     * 0：游客1:电动T车2：混动T车3：燃油T车4：非T车）
     */
    fun getAllPanelDataList(userVehType: String): List<ArrayList<String>> {
        val list = mutableListOf<ArrayList<String>>()
        when (userVehType) {
            "1" -> {//电动车
                list.add(pm)
                list.add(wd)
                list.add(xhlc)
                list.add(percent)
                list.add(percent)
            }
            "2" -> {//混动车
                list.add(pm)
                list.add(wd)
                list.add(oil)
                list.add(percent)
                list.add(percent)
            }
            "3" -> {//燃油车
                list.add(pm)
                list.add(wd)
                list.add(oil)
                list.add(percent)
                list.add(percent)
            }
            "4" -> {//非t车
                list.add(pm)
                list.add(wd)
                //注册App天数
                list.add(zcts)
                list.add(percent)
                list.add(percent)
            }
            else -> {//游客
                list.add(pm)
                list.add(wd)
                //注册天数
                list.add(zcts)
                list.add(percent)
                list.add(percent)
            }

        }
        return list
    }

    /**
     * 获取真实的角度
     */
    fun getRealCorner(userVehType: String, position: Int, value: Int): Int {
        val list = getAllPanelDataList(userVehType)[position]

        return when (position) {
            2 -> {
                if (value < list[0].toInt()) {
                    list[0].toInt()
                } else if (value <= list[list.size - 1].toInt()) {//小于后者等于最大角度
                    if ((userVehType == "0" || userVehType == "4")) {//潜客或者非T车  中间表盘为注册天数
                        when {
                            value <= 100 -> {
                                cornerList[position] * value / (list[list.size - 1].toInt() - list[0].toInt())
                            }
                            value <= 1000 -> {
                                cornerList[position] * (value - 100) / (list[list.size - 1].toInt() - list[0].toInt())
                            }
                            else -> {
                                val m = value / 1000
                                KLog.e(m.toString() + "m---", (cornerList[position] * (value - 1000 * m)).toString() + "-------" + (list[list.size - 1].toInt() - list[0].toInt()) + "aaaaaaaaaaaaaa" + cornerList[position] * (value - 1000 * m) / (list[list.size - 1].toInt() - list[0].toInt()))
                                cornerList[position] * (value - 1000 * m) / (list[list.size - 1].toInt() - list[0].toInt())

                            }
                        }
                    } else {
                        cornerList[position] * value / (list[list.size - 1].toInt() - list[0].toInt())
                    }
                } else {
                    cornerList[position]
                }
            }
            else -> {
                if (value < list[0].toInt()) {
                    list[0].toInt()
                } else if (value <= list[list.size - 1].toInt()) {//小于后者等于最大角度
                    if (position == 1) {//温度是从负数开始
                        cornerList[position] * (value + 30) / (list[list.size - 1].toInt() - list[0].toInt())
                    } else {
                        cornerList[position] * value / (list[list.size - 1].toInt() - list[0].toInt())
                    }
                } else {
                    cornerList[position]
                }
            }
        }
    }


    /**
     * 初始化注册天数
     */
    fun initRegisterDays(day: Int): ArrayList<String> {
        zcts.clear()
        when {
            day <= 100 -> {
                zcts = arrayListOf("0", "20", "40", "60", "80", "100", "120")
            }
            day <= 1000 -> {
                zcts = arrayListOf("100", "250", "400", "550", "700", "850", "1000")
            }
            else -> {
                val m = day / 1000
                for (item in 0..6) {
                    zcts.add((m * 1000 + item * 200).toString())
                }

            }
        }
        return zcts
    }
}