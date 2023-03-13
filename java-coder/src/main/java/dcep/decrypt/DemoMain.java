package dcep.decrypt;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class DemoMain {
	// 国密规范测试用户ID
	private static final String userId = "1234567812345678";
	// 国密规范测试私钥  银行私钥
	private static final String prik = "47B3596B08558852D65D8277D591FE342E581CE1A6B9CC948461F870446E2903";

	//国密规范测试公钥   银行公钥
	private static final String pubk = "0493E84497D7A77732A09BFCE531CD7E2FDCCA9139662D1D856B57269C8A4D2D16067E62DFBBE1E115287769AFF72BE9AF5945A659496E53C83EC23782C7437B7E";
	public static void main(String[] arg) {

		/**
		 * 生成随机密钥对
		 */
		SM2 sm2 = SM2.Instance();
		AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
		ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
		ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
		BigInteger privateKey = ecpriv.getD();
		ECPoint publicKey = ecpub.getQ();

		System.out.println("公钥: " + Util.byteToHex(publicKey.getEncoded()));
		System.out.println("私钥: " + Util.byteToHex(privateKey.toByteArray()));
		System.out.println("");

		String msg = "123456789000000";//原始数据
		System.out.println("原始数据："+msg);
		String summaryString = summary(msg);
		System.out.println("摘要："+summaryString);
		String signString = sign(summaryString);
		System.out.println("摘要签名："+signString);
		boolean status = verify(summaryString,signString);
		System.out.println("验签结果："+status);
		
		System.out.println("加密: ");
		byte[] cipherText = null;
		try {
			cipherText = SM2Utils.encrypt(Base64.decode(new String(Base64.encode(Util.hexToByte(pubk))).getBytes()), msg.getBytes());
		} catch (IllegalArgumentException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		System.out.println(new String(Base64.encode(cipherText)));
		System.out.println("");
		
		System.out.println("解密: ");
		String res = null;
		try {
			//工行的sm4 密文
			String cip = "MHoCIQCGfBHBj+J076dPbd0nGsGo+pWRudLaZeCnIsQGURFOWQIhAONjMySohhCcTi2oNl/j8Qzr43IRKk805qt9NfHqx2O4BCChrV7v6I8Ss8B1xBGQo8TZuToo7Okh2x/+KgQhrNewoQQQ55mMDdiPh1tGS56Zo0l4WA==";

//			res = new String(SM2Utils.decrypt(Base64.decode(new String(Base64.encode(Util.hexToByte(prik))).getBytes()), cipherText));
			//String cip = "MHECICkIWFtIcjCaFyk3Kj6DShv/VkCKfm4USc9ZT3npzhw6AiAJHV1kDrnTeB4JKHB8L3wwaBgOsZzazTfgHz4arGIV+QQg8Dihu2ucEObMR4CnUT9xhDU+4wyHAuKQbrUeApN9vTkECS0KTrrM5tb75A==";
//			res = new String(SM2Utils.decrypt(Base64.decode(new String(Base64.encode(Util.hexToByte(prik))).getBytes()), Base64.decode(cip.getBytes())));
			byte[] sm4keys = SM2Utils.decrypt(Base64.decode(new String(Base64.encode(Util.hexToByte(prik))).getBytes()), Base64.decode(cip.getBytes()));
			res = Util.byteToHex(sm4keys);

		} catch (IllegalArgumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println(res);
		
	}
	
	/**
	 * 摘要
	 * @return 
	 */
	public static String summary(String msg) {
		//1.摘要
		byte[] md = new byte[32];
		SM3Digest sm = new SM3Digest();
		sm.update(msg.getBytes(), 0, msg.getBytes().length);
		sm.doFinal(md, 0);
		String s = new String(Hex.encode(md));
		return s.toUpperCase();
	}
	
	/**
	  * 签名
	 * @return
	 */
	public static String sign(String summaryString) {
		String prikS = new String(Base64.encode(Util.hexToByte(prik)));
		System.out.println("prikS: " + prikS);
		System.out.println("");
		
		System.out.println("ID: " + Util.getHexString(userId.getBytes()));
		System.out.println("");
		System.out.println("签名: ");
		byte[] sign = null; //摘要签名
		try {
			sign = SM2Utils.sign(userId.getBytes(), Base64.decode(prikS.getBytes()), Util.hexToByte(summaryString));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Util.getHexString(sign);
	}
	
	/**
	 * 验签
	 * @return 
	 */
	public static boolean verify(String summary,String sign) {
		String pubkS = new String(Base64.encode(Util.hexToByte(pubk)));
		System.out.println("pubkS: " + pubkS);
		System.out.println("");
		
		System.out.println("验签 ");
		boolean vs = false; //验签结果
		try {
			vs = SM2Utils.verifySign(userId.getBytes(), Base64.decode(pubkS.getBytes()), Util.hexToByte(summary), Util.hexToByte(sign));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vs;
	}

}


