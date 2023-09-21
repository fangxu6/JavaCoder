package com.jse.commons.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ZqQRCodeDecoder {

	public ZqQRCodeDecoder() {

	}

	public static void main(String[] args) {
		try {
			 System.out.println(decodeByZxing("D:\\doc\\data.jpg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BufferedImage encode(String url) throws WriterException, IOException {
		int width = 250; // 图像宽度
		int height = 250; // 图像高度
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	// 指定长宽
	public static BufferedImage encode(String url, int width, int height) throws WriterException, IOException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	/**
	 * 
	 * com.google.zxing
	 * 
	 * 解析图像
	 * 
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static String decodeByZxing(String filePath) throws NotFoundException, IOException {
		BufferedImage image;
		image = ImageIO.read(new File(filePath));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
		return result.getText();
	}
	
	

	/**
	 * 
	 * com.google.zxing
	 * 
	 * 解析图像
	 * 
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static String decodeByZxing(byte[] bytes) throws NotFoundException, IOException {
		BufferedImage image;
		InputStream in = new ByteArrayInputStream(bytes);
		image = ImageIO.read(in);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

		Result result;
		try {
			result = new MultiFormatReader().decode(binaryBitmap, hints);
		} catch (Exception e) {
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			//hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
			result = new MultiFormatReader().decode(binaryBitmap, hints);
		}

		return result.getText();
	}

	/**
	 * decode qrcode image.
	 * 
	 * @param qrcodePicfilePath
	 * @return decoding value.
	 */
	public static String decode(String qrcodePicfilePath) {
		/* 读取二维码图像数据 */
		File imageFile = new File(qrcodePicfilePath);

		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Decoding failed, read QRCode image error: " + e.getMessage());
			return null;
		}
		/* 解二维码 */
		QRCodeDecoder decoder = new QRCodeDecoder();

		String decodedData = new String(decoder.decode(new J2SEImageGucas(image)));
		return decodedData;
	}

	public static String decode(byte[] bytes) {
		QRCodeDecoder decoder = new QRCodeDecoder();
		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return new String(decoder.decode(new J2SEImageGucas(image)));

	}
}

class J2SEImageGucas implements QRCodeImage {
	BufferedImage	image;

	public J2SEImageGucas(BufferedImage image) {
		this.image = image;
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}
}
