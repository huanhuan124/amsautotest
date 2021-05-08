package com.car.library;

import java.util.Random;

public class randomUtil {
    //定义字母和数字
    public static String randomBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomNumberBase = "0123456789";

    //Unicode 基本汉字编码范围0x4e00~0x9fa5 共 20902个，这个是从网上查到的。因此，我们要把这个随机的范围给计算出来。
//    private final static int HANZI_LENGTH = 20902;

//    public static Random random = new Random();


    public static String[] citys = {"京", "津"};
    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H",  "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};




    /**
     * 随机生成车牌
     */
    public static String getRandomvehicleNo() {
//        String[] citys = {"津","京"};
        while (true) {
            //随机取津、京
            String city = citys[(new Random().nextInt(2))];
            String letter = letters[new Random().nextInt(24)];
            StringBuffer plateNumStr = new StringBuffer(city);//下面开始：拼接车牌号，并随机产生0到9的6位车牌号
            plateNumStr.append(letter)
                    .append(new Random().nextInt(10))
                    .append(new Random().nextInt(10))
                    .append(new Random().nextInt(10))
                    .append(new Random().nextInt(10))
                    .append(new Random().nextInt(10));
            return plateNumStr.toString();

        }


    }

    /**
     * 随机生成车架号
     */

    public static String getRandomframeNo(){
        String frameNoletter = "JSZDKCAR";
        int randomNumber = new Random().nextInt(999999999-111111111)+111111111;
//        System.out.println(randomNumber);
        String randomframeNo = frameNoletter + randomNumber;
        return randomframeNo;
    }


}