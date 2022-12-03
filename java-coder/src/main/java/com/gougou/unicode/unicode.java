package com.gougou.unicode;

/**
 * @author fangxu
 * on date:2022/6/27
 */
public class unicode {
    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }
    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }
    /**
     * 测试字符串长度
     */
    public static void stringLength(String string) {
        System.out.println("String is = " + string+" ,default length = "+ string.length());
        System.out.println("String is = " + string+" ,getBytes length = "+ string.getBytes().length);
        System.out.println("String 中包含 " + (string.getBytes().length-string.length())+" 个中文");
    }
    public static void main(String[] args) {
        String test = "中文ab";
        String unicode = string2Unicode(test);
        String string = unicode2String(unicode) ;
        System.out.println(unicode);
        System.out.println(string);
        String test1 = "中文";
        String test2 = "ab";
        stringLength(test);
        stringLength(test1);
        stringLength(test2);
    }
}
