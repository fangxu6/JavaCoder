package jnsw.copy.util;

import org.apache.commons.codec.binary.Base64;

public class SignUtil {

    public static String makeSignature(String appid, String appsecret, String timestamp, String data) {
        String s = new StringBuffer(appid).append(appsecret).append(timestamp).append(data).toString();
        return MD5.toMD5(s);
    }

    public static String encrypt(String data, String apisecret) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        return Base64.encodeBase64String(AES.encrypt(bytes, apisecret));
    }

    public static String decrypt(String endata, String apisecret) throws Exception {
        String result = new String(AES.decrypt(Base64.decodeBase64(endata), apisecret), "UTF-8");
        return result;
    }

}