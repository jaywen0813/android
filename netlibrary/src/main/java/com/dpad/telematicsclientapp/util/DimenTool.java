package com.dpad.telematicsclientapp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DimenTool {
    public static void gen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./app/src/main/res/values/dimens.xml");
        BufferedReader reader = null;

        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw360 = new StringBuilder();
        StringBuilder sw375 = new StringBuilder();
        StringBuilder sw384 = new StringBuilder();

        StringBuilder sw392 = new StringBuilder();
        StringBuilder sw400 = new StringBuilder();
        StringBuilder sw410 = new StringBuilder();
        StringBuilder sw411 = new StringBuilder();
        StringBuilder sw432 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw533 = new StringBuilder();
        StringBuilder sw592 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw640 = new StringBuilder();
        StringBuilder sw662 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw768 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder sw811 = new StringBuilder();
        StringBuilder sw820 = new StringBuilder();
        StringBuilder sw960 = new StringBuilder();
        StringBuilder sw961 = new StringBuilder();
        StringBuilder sw1024 = new StringBuilder();
        StringBuilder sw1280 = new StringBuilder();
        StringBuilder sw1365 = new StringBuilder();
        StringBuilder sw4285 = new StringBuilder();
        StringBuilder sw7272 = new StringBuilder();
        try {
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                            (tempString.substring(tempString.indexOf(">") + 1,
                                    tempString.indexOf("</dimen>") - 2));
                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。

                    sw320.append(start).append(num * 0.8533).append(end).append("\r\n");
                    sw360.append(start).append(num * 0.9600).append(end).append("\r\n");
                    sw375.append(start).append(num * 1.0000).append(end).append("\r\n");
                    sw384.append(start).append(num * 1.024).append(end).append("\r\n");


                    sw392.append(start).append(num * 1.0453).append(end).append("\r\n");

                    sw400.append(start).append(num * 1.0667).append(end).append("\r\n");
                    sw410.append(start).append(num * 1.0933).append(end).append("\r\n");
                    sw411.append(start).append(num * 1.0960).append(end).append("\r\n");
                    sw432.append(start).append(num * 1.1520).append(end).append("\r\n");

                    sw480.append(start).append(num * 1.2800).append(end).append("\r\n");

                    sw533.append(start).append(num * 1.4213).append(end).append("\r\n");
                    sw592.append(start).append(num * 1.5787).append(end).append("\r\n");


                    sw600.append(start).append(num * 1.6000).append(end).append("\r\n");

                    sw640.append(start).append(num * 1.7067).append(end).append("\r\n");
                    sw662.append(start).append(num * 1.7653).append(end).append("\r\n");

                    sw720.append(start).append(num * 1.9200).append(end).append("\r\n");

                    sw768.append(start).append(num * 2.0480).append(end).append("\r\n");


                    sw800.append(start).append(num * 2.1333).append(end).append("\r\n");
                    sw811.append(start).append(num * 2.1627).append(end).append("\r\n");
                    sw820.append(start).append(num * 2.1867).append(end).append("\r\n");

                    sw960.append(start).append(num * 2.5600).append(end).append("\r\n");
                    sw961.append(start).append(num * 2.5627).append(end).append("\r\n");
                    sw1024.append(start).append(num *2.7307 ).append(end).append("\r\n");
                    sw1280.append(start).append(num *3.4133 ).append(end).append("\r\n");
                    sw1365.append(start).append(num *3.6400 ).append(end).append("\r\n");
                    sw4285.append(start).append(num *11.4267 ).append(end).append("\r\n");
                    sw7272.append(start).append(num * 19.3920).append(end).append("\r\n");

                } else {
                    sw392.append(tempString).append("");
                    sw480.append(tempString).append("");
                    sw600.append(tempString).append("");
                    sw720.append(tempString).append("");
                    sw800.append(tempString).append("");
                    sw820.append(tempString).append("");
                }
                line++;
            }
            reader.close();
            System.out.println("<!--  sw392 -->");
            System.out.println(sw392);
//            System.out.println("<!--  sw480 -->");
//            System.out.println(sw480);
//            System.out.println("<!--  sw600 -->");
//            System.out.println(sw600);
//            System.out.println("<!--  sw720 -->");
//            System.out.println(sw720);
//            System.out.println("<!--  sw800 -->");
//            System.out.println(sw800);

            String sw320file = "./app/src/main/res/values-sw320dp/dimens.xml";
            String sw360file = "./app/src/main/res/values-sw360dp/dimens.xml";
            String sw375file = "./app/src/main/res/values-sw375dp/dimens.xml";
            String sw384file = "./app/src/main/res/values-sw384dp/dimens.xml";


            String sw392file = "./app/src/main/res/values-sw392dp/dimens.xml";

            String sw400file = "./app/src/main/res/values-sw400dp/dimens.xml";
            String sw410file = "./app/src/main/res/values-sw410dp/dimens.xml";
            String sw411file = "./app/src/main/res/values-sw411dp/dimens.xml";
            String sw432file = "./app/src/main/res/values-sw432dp/dimens.xml";
            String sw480file = "./app/src/main/res/values-sw480dp/dimens.xml";
            String sw533file = "./app/src/main/res/values-sw533dp/dimens.xml";
            String sw592file = "./app/src/main/res/values-sw592dp/dimens.xml";
            String sw600file = "./app/src/main/res/values-sw600dp/dimens.xml";
            String sw640file = "./app/src/main/res/values-sw640dp/dimens.xml";
            String sw662file = "./app/src/main/res/values-sw662dp/dimens.xml";
            String sw720file = "./app/src/main/res/values-sw720dp/dimens.xml";
            String sw768file = "./app/src/main/res/values-sw768dp/dimens.xml";

            String sw800file = "./app/src/main/res/values-sw800dp/dimens.xml";
            String sw811file = "./app/src/main/res/values-sw811dp/dimens.xml";
            String sw820file = "./app/src/main/res/values-sw820dp/dimens.xml";
            String sw960file = "./app/src/main/res/values-sw960dp/dimens.xml";
            String sw961file = "./app/src/main/res/values-sw961dp/dimens.xml";
            String sw1024file = "./app/src/main/res/values-sw1024dp/dimens.xml";
            String sw1280file = "./app/src/main/res/values-sw1280dp/dimens.xml";


            String sw1365file = "./app/src/main/res/values-sw1365dp/dimens.xml";
            String sw4285file = "./app/src/main/res/values-sw4285dp/dimens.xml";
            String sw7272file = "./app/src/main/res/values-sw7272dp/dimens.xml";

            //将新的内容，写入到指定的文件中去
            writeFile(sw320file, sw320.toString());
            writeFile(sw360file, sw360.toString());
            writeFile(sw375file, sw375.toString());
            writeFile(sw384file, sw384.toString());

            writeFile(sw392file, sw392.toString());

            writeFile(sw400file, sw400.toString());
            writeFile(sw410file, sw410.toString());
            writeFile(sw411file, sw411.toString());
            writeFile(sw432file, sw432.toString());
            writeFile(sw480file, sw480.toString());
            writeFile(sw533file, sw533.toString());
            writeFile(sw592file, sw592.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw640file, sw640.toString());
            writeFile(sw662file, sw662.toString());
            writeFile(sw720file, sw720.toString());
            writeFile(sw768file, sw768.toString());
            writeFile(sw800file, sw800.toString());
            writeFile(sw811file, sw811.toString());

            writeFile(sw820file, sw820.toString());
            writeFile(sw960file, sw960.toString());
            writeFile(sw961file, sw961.toString());
            writeFile(sw1024file, sw1024.toString());
            writeFile(sw1280file, sw1280.toString());

            writeFile(sw1365file, sw1365.toString());
            writeFile(sw4285file, sw4285.toString());
            writeFile(sw7272file, sw7272.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    public static void main(String[] args) {
        gen();
    }


}
