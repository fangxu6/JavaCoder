package com.gougou.utils;


public class MyBytes {
    /**
     * int转byte[]
     */
    public static byte[] intToBytes(int number, int digits) {
        byte[] result = new byte[digits];
        /* Start at the end of the array. i.e. 1234, should be converted to {1, 2, 3, 4} */
        for (int i = digits - 1; i >= 0; i--) {
            result[i] = (byte) (number % 10);
            number = number / 10;
        }
        return result;
    }

    /**
     * int转byte[] of ascii
     */
    public static byte[] intToBytesOfAscii(int number, int digits) {
        String format = "%0" + digits + "d";
        String numberAsString = String.format(format, number);
        char[] numberAsCharArray = numberAsString.toCharArray();
        byte[] result = new byte[digits];
        for (int i = 0; i < numberAsCharArray.length; i++) {
            result[i] = (byte) numberAsCharArray[i];
        }
        return result;
    }

}
