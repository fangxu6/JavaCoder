package jnsw.copy.util;

import java.util.Formatter;

public class CryptUtils {
    public CryptUtils() {
    }

    public static String byte2Hex(byte b) {
        String hex = Integer.toHexString(b);
        if (hex.length() > 2) {
            hex = hex.substring(hex.length() - 2);
        }

        while (hex.length() < 2) {
            hex = "0" + hex;
        }

        return hex;
    }

    public static String byte2Hex(byte[] bytes) {
        Formatter formatter = new Formatter();
        byte[] var5 = bytes;
        int var4 = bytes.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            formatter.format("%02x", b);
        }

        String hash = formatter.toString();
        formatter.close();
        return hash;
    }
}

