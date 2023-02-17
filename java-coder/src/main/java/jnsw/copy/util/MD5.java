package jnsw.copy.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private MessageDigest md5;
    private boolean init = false;

    private void init() {
        if (!this.init) {
            try {
                this.md5 = MessageDigest.getInstance("MD5");
                this.init = true;
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }

    }

    public MD5() {
        this.init();
    }

    public MD5(String inStr) {
        this.init();
        this.md5.update(inStr.getBytes());
    }

    public MD5(byte[] input) {
        this.init();
        this.md5.update(input);
    }

    public void update(byte[] input) {
        this.md5.update(input);
    }

    public String compute() {
        byte[] md5Bytes = this.md5.digest();
        StringBuffer hexValue = new StringBuffer();
        byte[] var6 = md5Bytes;
        int var5 = md5Bytes.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            byte md5Byte = var6[var4];
            int val = md5Byte & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static String toMD5(String plainText) {
        if (plainText == null) {
            return null;
        } else {
            try {
                byte[] data = plainText.getBytes("UTF-8");
                return md5String(data);
            } catch (UnsupportedEncodingException var2) {
                return null;
            }
        }
    }

    public static String md5String(byte[] data) {
        String md5Str = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance(Algorithm.MD5.getKey());
            byte[] buf = md5.digest(data);
            byte[] var7 = buf;
            int var6 = buf.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                byte element = var7[var5];
                md5Str = md5Str + CryptUtils.byte2Hex(element);
            }
        } catch (NoSuchAlgorithmException var8) {
        }

        return md5Str;
    }
}

