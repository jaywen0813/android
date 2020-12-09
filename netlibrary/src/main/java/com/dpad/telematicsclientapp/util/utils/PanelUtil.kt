package com.dpad.crmclientapp.android.util.utils

import com.socks.library.KLog
import java.util.*

/**
 * ================================================
 * ��    �ߣ�wenbody
 * ��    ����1.0
 * �������ڣ�2019/12/27 14:47
 * ��    ����
 * ================================================
 */
object PanelUtil {

    private val pm = arrayListOf("0", "50", "100", "150", "200", "250")
    private val wd = arrayListOf("-30", "-15", "0", "15", "30", "45")
    private val xhlc = arrayListOf("0", "60", "120", "180", "240", "300", "360")
    private val percent = arrayListOf("0", "20", "40", "60", "80", "100")
    private val oil = arrayListOf("0", "16", "32", "48", "64", "80", "100")

    private var zcts = arrayListOf("0", "16", "32", "48", "64", "80", "100")//ע������


    private val visitorPanelName = arrayListOf("��������", "��������", "����������", "����ʪ��", "�ܱ��û�")//�ο�
    private val nomalPanelName = arrayListOf("��������", "��������", "����������", "����ʪ��", "�ܱ��û�")//��T�û�
    private val fuelPanelName = arrayListOf("��������", "��������", "ʣ������", "�ͺĻ���", "�ܱ��û�")//ȼ�ͳ�
//    private val electricallyPanelName = arrayListOf("��������", "��������", "�������", "���ܵ�Ļ���", "�ܱ��û�")//�綯��
    private val electricallyPanelName = arrayListOf("��������", "��������", "�������", "ʣ�����", "�ܱ��û�")//�綯��
    private val hybridPanelName = arrayListOf("��������", "��������", "ʣ�����", "ʣ������", "�ͺĻ���")//��ϳ�


    private val visitorUnitName = arrayListOf("PM2.5", "��", "Day", "%", "User")//�ο�
    private val nomalUnitName = arrayListOf("PM2.5", "��", "Day", "%", "User")//��T�û�
    private val fuelUnitName = arrayListOf("PM2.5", "��", "%", "%", "User")//ȼ�ͳ�
    private val electricallyUnitName = arrayListOf("PM2.5", "��", "km", "%", "User")//�綯��
    private val hybridUnitName = arrayListOf("PM2.5", "��", "%", "%", "%")//��ϳ�


    private val cornerList = arrayListOf(225, 225, 270, 225, 215)

    /**
     * 0���ο�1:�綯T��2���춯T��3��ȼ��T��4����T����
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
     * 0���ο�1:�綯T��2���춯T��3��ȼ��T��4����T����
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
     * 0���ο�1:�綯T��2���춯T��3��ȼ��T��4����T����
     */
    fun getAllPanelDataList(userVehType: String): List<ArrayList<String>> {
        val list = mutableListOf<ArrayList<String>>()
        when (userVehType) {
            "1" -> {//�綯��
                list.add(pm)
                list.add(wd)
                list.add(xhlc)
                list.add(percent)
                list.add(percent)
            }
            "2" -> {//�춯��
                list.add(pm)
                list.add(wd)
                list.add(oil)
                list.add(percent)
                list.add(percent)
            }
            "3" -> {//ȼ�ͳ�
                list.add(pm)
                list.add(wd)
                list.add(oil)
                list.add(percent)
                list.add(percent)
            }
            "4" -> {//��t��
                list.add(pm)
                list.add(wd)
                //ע��App����
                list.add(zcts)
                list.add(percent)
                list.add(percent)
            }
            else -> {//�ο�
                list.add(pm)
                list.add(wd)
                //ע������
                list.add(zcts)
                list.add(percent)
                list.add(percent)
            }

        }
        return list
    }

    /**
     * ��ȡ��ʵ�ĽǶ�
     */
    fun getRealCorner(userVehType: String, position: Int, value: Int): Int {
        val list = getAllPanelDataList(userVehType)[position]

        return when (position) {
            2 -> {
                if (value < list[0].toInt()) {
                    list[0].toInt()
                } else if (value <= list[list.size - 1].toInt()) {//С�ں��ߵ������Ƕ�
                    if ((userVehType == "0" || userVehType == "4")) {//Ǳ�ͻ��߷�T��  �м����Ϊע������
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
                } else if (value <= list[list.size - 1].toInt()) {//С�ں��ߵ������Ƕ�
                    if (position == 1) {//�¶��ǴӸ�����ʼ
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
     * ��ʼ��ע������
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