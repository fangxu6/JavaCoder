package com.jse.commons.util;

import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
	private static final ZqLogger	logger	= new ZqLogger(ImageUtil.class);

	public static void main(String[] args) throws Exception {

		System.out.println(getImageBase64("C:/test/wanlian.bmp"));

		base64StringToImage(getImageBase64("C:/test/wanlian.bmp"));

	}

	public static String getImageBase64(String filePath) {
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				return null;
			}
			BufferedImage bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", baos);
			byte[] bytes = baos.toByteArray();
			return Base64.encodeBase64String(bytes).trim();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static BufferedImage base64StringToImage(String base64String) {
		try {
			byte[] bytes1 = Base64.decodeBase64(base64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			return bi1;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
