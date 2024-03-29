package dcep.decrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;

import org.bouncycastle.asn1.*;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class SM2Utils {
	public static byte[] encrypt(byte[] publicKey, byte[] data) throws IOException {
		if (publicKey == null || publicKey.length == 0) {
			return null;
		}
		
		if (data == null || data.length == 0) {
			return null;
		}
		
		byte[] source = new byte[data.length];
		System.arraycopy(data, 0, source, 0, data.length);
		
		Cipher cipher = new Cipher();
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
		
		ECPoint c1 = cipher.Init_enc(sm2, userKey);
		cipher.Encrypt(source);
		byte[] c3 = new byte[32];
		cipher.Dofinal(c3);
		
		DERInteger x = new DERInteger(c1.getX().toBigInteger());
		DERInteger y = new DERInteger(c1.getY().toBigInteger());
		DEROctetString derDig = new DEROctetString(c3);
		DEROctetString derEnc = new DEROctetString(source);
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(x);
		v.add(y);
		v.add(derDig);
		v.add(derEnc);
		DERSequence seq = new DERSequence(v);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DEROutputStream dos = new DEROutputStream(bos);
		dos.writeObject(seq);
		return bos.toByteArray();
	}
	
	public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
		if (privateKey == null || privateKey.length == 0) {
			return null;
		}
		
		if (encryptedData == null || encryptedData.length == 0) {
			return null;
		}
		
		byte[] enc = new byte[encryptedData.length];
		System.arraycopy(encryptedData, 0, enc, 0, encryptedData.length);
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(1, privateKey);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(enc);
		ASN1InputStream dis = new ASN1InputStream(bis);
		DERObject derObj = dis.readObject();
		ASN1Sequence asn1 = (ASN1Sequence) derObj;
		DERInteger x = (DERInteger) asn1.getObjectAt(0);
		DERInteger y = (DERInteger) asn1.getObjectAt(1);
		ECPoint c1 = sm2.ecc_curve.createPoint(x.getValue(), y.getValue(), true);
		
		Cipher cipher = new Cipher();
		cipher.Init_dec(userD, c1);
		DEROctetString data = (DEROctetString) asn1.getObjectAt(3);
		enc = data.getOctets();
		cipher.Decrypt(enc);
		byte[] c3 = new byte[32];
		cipher.Dofinal(c3);
		return enc;
	}
	
	public static byte[] sign(byte[] userId, byte[] privateKey, byte[] sourceData) throws IOException {
		if (privateKey == null || privateKey.length == 0) {
			return null;
		}
		
		if (sourceData == null || sourceData.length == 0) {
			return null;
		}
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(privateKey);
		System.out.println("userD: " + userD.toString(16));
		System.out.println("");
		
		ECPoint userKey = sm2.ecc_point_g.multiply(userD);
		System.out.println("椭圆曲线点X:" + userKey.getX().toBigInteger().toString(16));
		System.out.println("椭圆曲线点Y:" + userKey.getY().toBigInteger().toString(16));
		System.out.println("");
		
		SM3Digest sm3 = new SM3Digest();
		byte[] z = sm2.sm2GetZ(userId, userKey);
		System.out.println("SM3摘要Z:" + Util.getHexString(z));
	    System.out.println("");
	    
	    System.out.println("M: " + Util.getHexString(sourceData));
		System.out.println("");
		
		sm3.update(z, 0, z.length);
	    sm3.update(sourceData, 0, sourceData.length);
	    byte[] md = new byte[32];
	    sm3.doFinal(md, 0);
	    
	    System.out.println("SM3摘要值:" + Util.getHexString(md));
	    System.out.println("");
	    
	    SM2Result sm2Result = new SM2Result();
	    sm2.sm2Sign(md, userD, userKey, sm2Result);
	    System.out.println("r: " + sm2Result.r.toString(16));
	    System.out.println("s: " + sm2Result.s.toString(16));
	    System.out.println("");
	    
	    DERInteger d_r = new DERInteger(sm2Result.r);
	    DERInteger d_s = new DERInteger(sm2Result.s);
	    ASN1EncodableVector v2 = new ASN1EncodableVector();
	    v2.add(d_r);
	    v2.add(d_s);
	    DERObject sign = new DERSequence(v2);
	    byte[] signdata = sign.getDEREncoded();
		return signdata;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean verifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData) throws IOException {
		if (publicKey == null || publicKey.length == 0) {
			return false;
		}
		
		if (sourceData == null || sourceData.length == 0) {
			return false;
		}
		
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
		
		SM3Digest sm3 = new SM3Digest();
		byte[] z = sm2.sm2GetZ(userId, userKey);
		sm3.update(z, 0, z.length);
		sm3.update(sourceData, 0, sourceData.length);
	    byte[] md = new byte[32];
	    sm3.doFinal(md, 0);
	    System.out.println("SM3摘要值:" + Util.getHexString(md));
	    System.out.println("");
		
	    ByteArrayInputStream bis = new ByteArrayInputStream(signData);
	    ASN1InputStream dis = new ASN1InputStream(bis);
	    DERObject derObj = dis.readObject();
	    Enumeration<DERInteger> e = ((ASN1Sequence) derObj).getObjects();
	    BigInteger r = ((DERInteger)e.nextElement()).getValue();
	    BigInteger s = ((DERInteger)e.nextElement()).getValue();
	    SM2Result sm2Result = new SM2Result();
	    sm2Result.r = r;
	    sm2Result.s = s;
	    System.out.println("r: " + sm2Result.r.toString(16));
	    System.out.println("s: " + sm2Result.s.toString(16));
	    System.out.println("");
	    
	    
	    sm2.sm2Verify(md, userKey, sm2Result.r, sm2Result.s, sm2Result);
        return sm2Result.r.equals(sm2Result.R);
	}
	
	public static void main(String[] args) throws Exception {
		//String plainText = "message digest";
		//byte[] sourceData = plainText.getBytes();

		// 国密规范测试私钥
		String prik = "";
		String prikS = new String(Base64.encode(Util.hexToByte(prik)));
		System.out.println("prikS: " + prikS);
		System.out.println("");

		// userId
		String userId = "1234567812345678";

		System.out.println("ID: " + Util.getHexString(userId.getBytes()));
		System.out.println("");

		// 
		System.out.println("签名: ");
		String SrcData_re= "";
		byte[] sourceData = SrcData_re.getBytes("utf8");
		String privatekey_CounterParty = "";
		byte[] sign = SM2Utils.sign(userId.getBytes(), privatekey_CounterParty.getBytes(), sourceData);
		System.out.println("sign: " + Util.getHexString(sign));
		System.out.println("");

		// 国密规范测试公钥
		String pubk = "";
		String pubkS = new String(Base64.encode(Util.hexToByte(pubk)));
		System.out.println("pubkS: " + pubkS);
		System.out.println("");


		System.out.println("验签 ");
		//byte[] signature_server = Base64.decode(signature_base64);
		String SrcData = "";
		byte[] SrcDataj = SrcData.getBytes("utf8");
		byte[] signature_sign = Base64.decode("");
		boolean vs = SM2Utils.verifySign(userId.getBytes(), Base64.decode(pubkS.getBytes()), SrcDataj, signature_sign);
		System.out.println("验签结果： " + vs);
		System.out.println("");

		System.out.println("加密: ");
		//byte[] cipherText = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), sourceData);
		byte[] cipherText = "".getBytes();
		System.out.println(new String(cipherText));
		System.out.println("");

		System.out.println("解密: ");
		byte[] plainText = SM2Utils.decrypt(Base64.decode(prikS.getBytes()), Base64.decode(cipherText));
		System.out.println("dec result hex 展示： " + new String(Hex.encode(plainText)));
		System.out.println("dec result 展示： " + new String(plainText));
	}
}