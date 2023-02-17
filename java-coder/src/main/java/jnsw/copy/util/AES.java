package jnsw.copy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public abstract class AES {

    public static Logger logger = LoggerFactory.getLogger(AES.class);

    /**
     * 加密
     *
     * @param bytes
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] bytes, String sKey) throws Exception {
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(bytes);
    }

    /**
     * 解密
     *
     * @param bytes
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] bytes, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            try {
                return cipher.doFinal(bytes);
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "jkl;POIU1234++==";
        // 需要加密的字串
        String cSrc = "www.gowhere.so";
        System.out.println(cSrc);
        // 加密
        byte[] encrypt = AES.encrypt(cSrc.getBytes("UTF-8"), cKey);
        //System.out.println("加密后的字串是：" + enString);

        // 解密
        byte[] decrypt = AES.decrypt(encrypt, cKey);
        System.out.println("解密后的字串是：" + new String(decrypt));
    }
}

