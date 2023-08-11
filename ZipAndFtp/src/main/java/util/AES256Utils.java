package util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.security.Key;

/**
 * className: AES256Utils
 * package: util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/11 16:14
 */
@SuppressWarnings("unused")
public class AES256Utils {

    /**
     * 密钥算法 java6支持56位密钥，bouncycastle支持64位
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * <p>
     * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";


    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(String key) throws Exception {
        // 实例化DES密钥
        // 生成密钥
        byte[] raw = key.getBytes("ASCII");
        SecretKeySpec secretKey = new SecretKeySpec(raw, "AES");
        // SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        /**
         * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String enc = new BASE64Encoder().encode(encrypted);
        return enc;
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        // 欢迎密钥
        Key k = toKey(key);
        /**
         * 实例化使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

        // 执行操作
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");

        return originalString;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

        String data2 = "123456";
        // 初始化密钥
        String skey = "yoXsBdByZT8De1rNiMl2pw==";
        try {

            // 信息加密
            String decryptkey = AES256Utils.encrypt(data2, skey);
            System.out.println("加密后的key值:" + decryptkey);

            // 解密数据
            String data = AES256Utils.decrypt(decryptkey, skey);
            System.out.println("解密后的key值:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
