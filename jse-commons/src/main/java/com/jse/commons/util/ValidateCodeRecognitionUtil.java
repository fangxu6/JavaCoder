package com.jse.commons.util;

import com.zhuanqian.commons.enums.SecurityCompany;
import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateCodeRecognitionUtil {
	private static final ZqLogger	logger	= new ZqLogger(ValidateCodeRecognitionUtil.class);

	public static void rename(String oldFilePath, String newFileName) {
		// TODO 自动生成方法存根
		File f = new File(oldFilePath);
		File mm = new File(newFileName);
		if (f.renameTo(mm)) {
			// System.out.println("修改成功！");
		} else {
			// System.out.println("修改失败！");
		}

	}

	public static String get() {
		Runtime rn = Runtime.getRuntime();
		Process pr = null;
		String fileName = null;
		try {
			pr = rn.exec("C:\\Users\\wei\\Desktop\\Debug123\\Debug\\test.exe get");

			BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String s = br.readLine();

			if (StringUtils.isNotBlank(s) && s.contains(":")) {
				fileName = s.substring(s.indexOf(":") + 1);
			}
			// while (null != s) {
			// if (!"".equals(s.trim()))
			// System.out.println(s);
			// s = br.readLine();
			// }
			br.close();
			// 导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。
			pr.waitFor();
			// 此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。

		} catch (Exception e) {
			System.out.println("Error exec!");
		}

		return StringUtils.trimToEmpty(fileName);
	}

	public static boolean check(String code) {
		Runtime rn = Runtime.getRuntime();
		Process pr = null;
		boolean isSuccess = false;
		try {
			pr = rn.exec("C:\\Users\\wei\\Desktop\\Debug123\\Debug\\test.exe check " + code + " 155 242");

			BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String s = br.readLine();
			s = s.trim();

			if (StringUtils.isNotBlank(s) && "sucess".equals(s)) {
				isSuccess = true;
			}
			// while (null != s) {
			// if (!"".equals(s.trim()))
			// System.out.println(s);
			// s = br.readLine();
			// }
			br.close();
			// 导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。
			pr.waitFor();
			// 此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。

		} catch (Exception e) {
			System.out.println("Error exec!");
		}
		return isSuccess;
	}

	public static List<String> recognitionByFilePathForXintuo(String filePath) throws Exception {
		BufferedImage image = ImageIO.read(new File(filePath));
		return recognitionByBufferedImageForXintuo(image);

	}

	public static List<String> recognitionByInputStreamForXintuo(InputStream in) throws Exception {
		BufferedImage image = ImageIO.read(in);
		return recognitionByBufferedImageForXintuo(image);

	}

	public static List<String> recognitionByBufferedImageForXintuo(BufferedImage image) throws Exception, IOException {
		// 设置背景色为白色
		image = changeBackGroudToWhiteImageForXintuo(image);

		image = changeToGrayImage(image);

		// ImageIO.write(image, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\aaaaaaaaaaaaa.png"));
		List<BufferedImage> listImg = splitImageForXintuo(image);// 切割图片
		List<String> codeStringList = new ArrayList<String>();

		// int i = 0;
		for (BufferedImage bi : listImg) {

			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\bbbbbbbbbbb_" + i + "_a.png"));

			bi = midddleValueFilter(0, true, bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\ccccccccccc_" + i + "_c.png"));
			bi = changeToBlackWhiteImage(bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\ddddddddddd_" + i + "_d.png"));
			bi = removeBadBlock(1, 1, 1, bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\eeeeeeeeeee_" + i + "_e.png"));
			bi = removeBlank(bi, false);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\fffffffffff_" + i + "_f.png"));
			// 缩放
			bi = zoom(10, 10, bi);

			// File file = new File("C:\\Users\\wei\\Desktop\\codeImagessss\\" + num + "_" + i + ".png");
			// ImageIO.write(bi, "png", file);
			//
			// i++;

			StringBuffer result = new StringBuffer();
			int width = bi.getWidth();
			int height = bi.getHeight();

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
						result.append("," + x + y + "1");
						// result.append("," + x + "_" + y + "_" + "1");
					} else {
						result.append("," + x + y + "0");
						// result.append("," + x + "_" + y + "_" + "0");
					}
				}
			}
			if (result.length() > 0) {
				result = result.deleteCharAt(0);
			}
			codeStringList.add(result.toString());
		}

		return codeStringList;
	}

	public static void main(String[] args) {
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(new File("C:\\Users\\wei\\Desktop\\codes\\x.bmp")));
			BufferedImage bi = removeBlank(image, false);
			ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\b1.png"));
			bi = changeToBlackWhiteImage(bi);
			ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\c1.png"));

			int beginX = 0;

			int count = 0;
			boolean preIsAllWhite = false;
			for (int x = bi.getWidth() - 1; x > 0; x--) {
				boolean findBeginX = true;
				for (int y = 0; y < bi.getHeight(); y++) {
					if (isWhiteForXintuo(bi.getRGB(x, y)) == 0) {
						findBeginX = false;
						preIsAllWhite = false;
						break;
					}
				}
				if (findBeginX && !preIsAllWhite) {
					preIsAllWhite = true;
					count++;
				}
				if (count == 3) {
					beginX = x;
					break;
				}
			}

			bi = bi.getSubimage(beginX + 1, 0, bi.getWidth() - beginX - 1, bi.getHeight());

			ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\f1.png"));

			StringBuffer result = new StringBuffer();
			int width = bi.getWidth();
			int height = bi.getHeight();

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
						result.append(x + "" + y + "1");
					} else {
						result.append(x + "" + y + "0");
					}
				}
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static List<String> recognitionByBufferedImageForTongHuaShun(BufferedImage image) throws Exception,
			IOException {
		// 设置背景色为白色
		image = changeBackGroudToWhiteImageForXintuo(image);

		image = changeToGrayImage(image);

		// ImageIO.write(image, "png", new File("C:\\Users\\wei\\Desktop\\codes\\aaaaaaaaaaaaa.png"));
		List<BufferedImage> listImg = splitImageForXintuo(image);// 切割图片
		List<String> codeStringList = new ArrayList<String>();

		// int i = 0;
		for (BufferedImage bi : listImg) {

			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\bbbbbbbbbbb_" + i + "_a.png"));

			bi = midddleValueFilter(0, true, bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\ccccccccccc_" + i + "_c.png"));
			bi = changeToBlackWhiteImage(bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\ddddddddddd_" + i + "_d.png"));
			bi = removeBadBlock(1, 1, 1, bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\eeeeeeeeeee_" + i + "_e.png"));
			bi = removeBlank(bi, false);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\fffffffffff_" + i + "_f.png"));
			// 缩放
			bi = zoom(10, 10, bi);

			// File file = new File("C:\\Users\\wei\\Desktop\\codes\\abc_" + i + ".png");
			// ImageIO.write(bi, "png", file);
			//
			// i++;

			StringBuffer result = new StringBuffer();
			int width = bi.getWidth();
			int height = bi.getHeight();

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
						result.append("," + x + y + "1");
						// result.append("," + x + "_" + y + "_" + "1");
					} else {
						result.append("," + x + y + "0");
						// result.append("," + x + "_" + y + "_" + "0");
					}
				}
			}
			if (result.length() > 0) {
				result = result.deleteCharAt(0);
			}

			System.out.println(result.toString());

			codeStringList.add(result.toString());
		}

		return codeStringList;
	}

	public static BufferedImage zoom(int width, int height, BufferedImage originalImage) {
		BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		originalImage = newImage;

		return originalImage;
	}

	public static List<BufferedImage> splitImageForXintuo(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();

		List<BufferedImage> imageList = new ArrayList<BufferedImage>();
		int beginX = 0;

		boolean findBeginX = true;
		for (int x = 0; x < width; x++) {

			boolean isFindEndX = true;
			for (int y = 0; y < height; y++) {
				if (findBeginX & isWhiteForXintuo(img.getRGB(x, y)) == 0) {
					beginX = x;
					findBeginX = false;
				}

				if (!findBeginX) {
					if (isWhiteForXintuo(img.getRGB(x, y)) == 1 || isWhiteForXintuo(img.getRGB(x + 1, y)) == 1) {
						continue;
					} else {
						isFindEndX = false;
						break;
					}
				}

			}

			if (!findBeginX && isFindEndX) {

				imageList.add(img.getSubimage(beginX, 0, x - beginX + 1, height));

				findBeginX = true;
				x++;
			}

			if (imageList.size() == 4) {
				break;
			}
		}

		if (imageList.size() == 3) {
			int maxWidthImageIndex = 0;
			int maxWidth = 0;
			for (int i = 0; i < imageList.size(); i++) {
				BufferedImage subImage = imageList.get(i);
				if (subImage.getWidth() > maxWidth) {
					maxWidthImageIndex = i;
					maxWidth = subImage.getWidth();
				}
			}

			BufferedImage toSplitSubImage = imageList.get(maxWidthImageIndex);

			int maxRed = 0;
			int toSecondSplitIndex = 0;
			for (int x = 2; x < toSplitSubImage.getWidth() - 2; x++) {
				int redTotal = 0;
				for (int y = 0; y < toSplitSubImage.getHeight(); y++) {
					redTotal += new Color(toSplitSubImage.getRGB(x, y)).getRed();
				}
				if (redTotal > maxRed) {
					maxRed = redTotal;
					toSecondSplitIndex = x;
				}
			}

			List<BufferedImage> totalImageList = new ArrayList<BufferedImage>();
			for (int i = 0; i < imageList.size(); i++) {
				if (i < maxWidthImageIndex) {
					totalImageList.add(imageList.get(i));
				}
			}

			totalImageList.add(toSplitSubImage.getSubimage(0, 0, toSecondSplitIndex, toSplitSubImage.getHeight()));
			totalImageList.add(toSplitSubImage.getSubimage(toSecondSplitIndex + 1, 0, toSplitSubImage.getWidth()
					- toSecondSplitIndex - 1, toSplitSubImage.getHeight()));

			for (int i = 0; i < imageList.size(); i++) {
				if (i >= maxWidthImageIndex + 1) {
					totalImageList.add(imageList.get(i));
				}
			}

			return totalImageList;

		}

		return imageList;

	}

	/**
	 * 
	 * @param img
	 * @param isWhiteBlank
	 *            true：去黑边false：去白边
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage removeBlank(BufferedImage img, boolean isRemoveBlackBlank) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();
		int startY = 0;
		int endY = 0;
		int startX = 0;
		int endX = 0;
		Label1: for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if (isRemoveBlackBlank) {
					if (isWhiteForXintuo(img.getRGB(x, y)) == 1) {
						startY = y;
						break Label1;
					}
				} else {
					if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
						startY = y;
						break Label1;
					}
				}

			}

		}
		Label2: for (int y = height - 1; y >= 0; --y) {
			for (int x = 0; x < width; ++x) {
				if (isRemoveBlackBlank) {
					if (isWhiteForXintuo(img.getRGB(x, y)) == 1) {
						endY = y;
						break Label2;
					}
				} else {
					if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
						endY = y;
						break Label2;
					}
				}
			}
		}

		Label3: for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isRemoveBlackBlank) {
					if (isWhiteForXintuo(img.getRGB(x, y)) == 1) {
						startX = x;
						break Label3;
					}
				} else {
					if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
						startX = x;
						break Label3;
					}
				}
			}
		}
		Label4: for (int x = width - 1; x >= 0; --x) {
			for (int y = 0; y < height; ++y) {
				if (isRemoveBlackBlank) {
					if (isWhiteForXintuo(img.getRGB(x, y)) == 1) {
						endX = x;
						break Label4;
					}
				} else {
					if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
						endX = x;
						break Label4;
					}
				}
			}
		}

		img = img.getSubimage(startX, startY, endX - startX + 1, endY - startY + 1);
		return img;
	}

	// public static int isBlack(int colorInt) {
	// Color color = new Color(colorInt);
	// if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
	// return 1;
	// }
	// return 0;
	// }

	public static int isBlackForXintuo(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() == 0) {
			return 1;
		}
		return 0;
	}

	// public static int isWhite(int colorInt) {
	// Color color = new Color(colorInt);
	// if (color.getRed() + color.getGreen() + color.getBlue() > 600) {
	// return 1;
	// }
	// return 0;
	// }

	public static int isWhiteForXintuo(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() == 765) {
			return 1;
		}
		return 0;
	}

	/**
	 * 变图像为黑白色 提示: 黑白化之前最好灰色化以便得到好的灰度平均值,利于获得好的黑白效果
	 *
	 * @return
	 */
	public static BufferedImage changeToBlackWhiteImage(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();

		int avgGrayValue = getAvgValue(image);
		int whitePoint = getWhitePoint(), blackPoint = getBlackPoint();

		Color point;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				point = new Color(image.getRGB(j, i));
				image.setRGB(j, i, (point.getRed() < avgGrayValue ? blackPoint : whitePoint));
			}
		}
		return image;
	}

	/**
	 * 变图像为黑白色 提示: 黑白化之前最好灰色化以便得到好的灰度平均值,利于获得好的黑白效果
	 *
	 * @return
	 */
	public static BufferedImage changeBackGroudToWhiteImageForXintuo(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();

		// int avgGrayValue = getAvgValue(image);
		int whitePoint = getWhitePoint();
		// int blackPoint = getBlackPoint();

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// if (isWhite(img.getRGB(x, y)) == 1)
				// continue;
				if (map.containsKey(image.getRGB(i, j))) {
					map.put(image.getRGB(i, j), map.get(image.getRGB(i, j)) + 1);
				} else {
					map.put(image.getRGB(i, j), 1);
				}
			}
		}
		int max = 0;
		int colorMax = 0;
		for (Integer color : map.keySet()) {
			if (max < map.get(color)) {
				max = map.get(color);
				colorMax = color;
			}
		}

		Color point;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				point = new Color(image.getRGB(j, i));

				if (point.getRGB() == colorMax) {
					image.setRGB(j, i, whitePoint);
				}

			}
		}
		return image;
	}

	/**
	 *
	 *
	 * @param whiteAreaPercent
	 *            过滤之后白色区域面积占整个图片面积的最小百分比
	 * @param removeLighter
	 *            true:过滤比中值颜色轻的,false:过滤比中值颜色重的,一般都是true
	 * @return
	 */
	public static BufferedImage midddleValueFilter(int whiteAreaMinPercent, boolean removeLighter, BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();
		int modify = 0;
		int avg = getAvgValue(image);
		Color point;
		while (getWhitePercent(image) < whiteAreaMinPercent) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					point = new Color(image.getRGB(j, i));
					if (removeLighter) {
						if (((point.getRed() + point.getGreen() + point.getBlue()) / 3) > avg - modify) {
							// System.out.println(((point.getRed() + point.getGreen() + point.getBlue()) / 3)+"--"+(avg - modify));
							image.setRGB(j, i, getWhitePoint());
						}
					} else {
						if (((point.getRed() + point.getGreen() + point.getBlue()) / 3) < avg + modify) {
							// System.out.println(((point.getRed() + point.getGreen() + point.getBlue()) / 3)+"--"+(avg - modify));
							image.setRGB(j, i, getWhitePoint());
						}
					}

				}
			}
			modify++;
		}
		// System.out.println(getWhitePercent());
		return image;
	}

	private static int getWhitePercent(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		Color point;
		int white = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				point = new Color(image.getRGB(j, i));
				if (((point.getRed() + point.getGreen() + point.getBlue()) / 3) == 255) {
					white++;
				}
			}
		}
		return (int) Math.ceil(((float) white * 100 / (width * height)));
	}

	/**
	 * @param 变图像为灰色
	 *            取像素点的rgb三色平均值作为灰度值
	 *
	 * @return
	 */

	public static BufferedImage changeToGrayImage(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		int gray;
		Color point;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				point = new Color(image.getRGB(j, i));
				gray = (point.getRed() + point.getGreen() + point.getBlue()) / 3;
				image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
			}
		}
		return image;
	}

	/**
	 *
	 * 去除噪点和单点组成的干扰线 注意: 去除噪点之前应该对图像黑白化
	 *
	 * @param neighborhoodMinCount
	 *            每个点最少的邻居数
	 * @return
	 */
	public static BufferedImage removeBadBlock(int blockWidth, int blockHeight, int neighborhoodMinCount,
			BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		int val;
		int whitePoint = getWhitePoint();
		int counter, topLeftXIndex, topLeftYIndex;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// 初始化邻居数为0
				counter = 0;
				topLeftXIndex = x - 1;
				topLeftYIndex = y - 1;
				// x1 y1是以x,y左上角点为顶点的矩形,该矩形包围在传入的矩形的外围,计算传入的矩形的有效邻居数目
				if (isBlackBlock(x, y, blockWidth, blockHeight, image)) {// 只有当块是全黑色才计算
					for (int x1 = topLeftXIndex; x1 <= topLeftXIndex + blockWidth + 1; x1++) {
						for (int y1 = topLeftYIndex; y1 <= topLeftYIndex + blockHeight + 1; y1++) {
							// 判断这个点是否存在
							if (x1 < width && x1 >= 0 && y1 < height && y1 >= 0) {
								// 判断这个点是否是传入矩形的外围点
								if (x1 == topLeftXIndex || x1 == topLeftXIndex + blockWidth + 1 || y1 == topLeftYIndex
										|| y1 == topLeftYIndex + blockHeight + 1) {
									// 这里假定图像已经被黑白化,取Red值认为不是0就是255
									val = new Color(image.getRGB(x1, y1)).getRed();
									// System.out.println(val + "--" + (centerVal));
									// 如果这个邻居是黑色,就把中心点的有效邻居数目加一
									if (val == 0) {
										counter++;
									}
								}
							}
						}
					}
					if (counter < neighborhoodMinCount) {
						image.setRGB(x, y, whitePoint);
					}
				}
			}
		}
		return image;
	}

	/**
	 * 如果点周围的黑点数达到补偿值就把这个点变为黑色
	 *
	 * @param addFlag
	 *            补偿阀值,通过观察处理过的图像确定,一般为2即可
	 * @return
	 */
	public static BufferedImage modifyBlank(int addFlag, BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		int val, counter = 0, topLeftXIndex, topLeftYIndex, blackPoint = getBlackPoint();
		Color point;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// 初始化邻居数为0
				counter = 0;
				topLeftXIndex = x - 1;
				topLeftYIndex = y - 1;
				point = new Color(image.getRGB(x, y));
				// 这里假定图像已经被黑白化,取Red值认为不是0就是255
				val = point.getRed();
				// 只有白点才进行补偿
				if (val == 255) {
					for (int x1 = topLeftXIndex; x1 <= topLeftXIndex + 2; x1++) {
						for (int y1 = topLeftYIndex; y1 <= topLeftYIndex + 2; y1++) {
							// 判断这个点是否存在
							if (x1 < width && x1 >= 0 && y1 < height && y1 >= 0) {
								// 判断这个点是否是传入点的外围点
								if (x1 == topLeftXIndex || x1 == topLeftXIndex + 2 || y1 == topLeftYIndex
										|| y1 == topLeftYIndex + 2) {
									// 这里假定图像已经被黑白化,取Red值认为不是0就是255
									val = new Color(image.getRGB(x1, y1)).getRed();
									// System.out.println(val + "--" + (centerVal));
									// 如果这个邻居是黑色,就把中心点的补偿数目加一
									if (val == 0) {
										counter++;
									}
								}
							}
						}
					}
					// 如果这个点周围的黑点数达到补偿值就把这个点变为黑色
					if (counter >= addFlag) {
						image.setRGB(x, y, blackPoint);
					}
				}
			}
		}
		return image;
	}

	public static BufferedImage getBufferedImage(String filename) {
		File file = new File(filename);
		try {
			return ImageIO.read(file);
		} catch (IOException ex) {
			// Logger.getLogger(ImageTool.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	private static boolean isBlackBlock(int startX, int startY, int blockWidth, int blockHeight, BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		int counter = 0;// 统计黑色像素点的个数
		int total = 0;// 统计有效像素点的个数
		int val;
		for (int x1 = startX; x1 <= startX + blockWidth - 1; x1++) {
			for (int y1 = startY; y1 <= startY + blockHeight - 1; y1++) {
				// 判断这个点是否存在
				if (x1 < width && x1 >= 0 && y1 < height && y1 >= 0) {
					total++;// 有效像素点的个数
					// 这里假定图像已经被黑白化,取Red值认为不是0就是255
					val = new Color(image.getRGB(x1, y1)).getRed();
					// 如果这个点是黑色,就把黑色像素点的数目加一
					if (val == 0) {
						counter++;
					}
				}
			}
		}
		// System.out.println(startX + "--" + startY + "" + (counter == total&&total!=0));
		return counter == total && total != 0;
	}

	private static int getWhitePoint() {
		return (new Color(255, 255, 255).getRGB() & 0xffffffff);
	}

	private static int getBlackPoint() {
		return (new Color(0, 0, 0).getRGB() & 0xffffffff);
	}

	private static int getAvgValue(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		Color point;
		int total = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				point = new Color(image.getRGB(j, i));
				total += (point.getRed() + point.getGreen() + point.getBlue()) / 3;
			}
		}
		return total / (width * height);
	}

	/**
	 * 从左往右边读取
	 * 
	 * @param img
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage getEntrustPrice(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();

		int beginX = 0;
		int endX = 0;
		int whiteColumnAppearTimes = 0;
		for (int x = 0; x < width; x++) {
			boolean isAllWhite = true;
			for (int y = 0; y < height; y++) {
				if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
					isAllWhite = false;
					break;
				}
			}

			if (isAllWhite) {
				whiteColumnAppearTimes++;
			}
			if (whiteColumnAppearTimes == 5) {
				endX = x;
				break;
			}

		}
		return img.getSubimage(beginX, 0, endX, height);
	}

	/**
	 * 从右往左边读取
	 * 
	 * @param img
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage getEntrustPriceFromRight(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();

		int startX = 0;
		int whiteColumnAppearTimes = 0;
		// 获取x 距离右边的距离
		for (int x = width - 1; x > 0; x--) {
			boolean isAllWhite = true;
			for (int y = 0; y < height; y++) {
				if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
					isAllWhite = false;
					break;
				}
			}

			if (isAllWhite) {
				whiteColumnAppearTimes++;
			}
			if (whiteColumnAppearTimes == 2) {
				startX = x;
				break;
			}
		}
		// 获取y距离
		int endY = 0;
		whiteColumnAppearTimes = 0;
		for (int y = 0; y < height; y++) {
			boolean isAllWhite = true;
			for (int x = startX; x < width; x++) {
				if (isBlackForXintuo(img.getRGB(x, y)) == 1) {
					isAllWhite = false;
					break;
				}
			}

			if (isAllWhite) {
				whiteColumnAppearTimes++;
			}
			if (whiteColumnAppearTimes == 1) {
				endY = y;
				break;
			}

		}
		return img.getSubimage(startX + 1, 0, width - startX - 1, endY);
	}

	public static String xinTuoImageEntrustPriceRecognition(InputStream in) throws Exception {

		BufferedImage bi = ImageIO.read(in);
		// BufferedImage bi = ImageIO.read(new File("C:\\Users\\wei\\Desktop\\qiguai\\rr.png"));
		// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\b.png"));
		bi = removeBlank(bi, false);
		// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\c.png"));
		bi = getEntrustPrice(bi);
		// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\d.png"));

		StringBuffer result = new StringBuffer();
		int width = bi.getWidth();
		int height = bi.getHeight();

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
					result.append(x + y + "1");
				} else {
					result.append(x + y + "0");
				}
			}
		}
		return result.toString();
		// System.out.println(StringUtils.equals(result.toString(), XinTu));

	}

	public static String xinTuoImageEntrustPriceRecognition(String filePath) {

		try {

			BufferedImage bi = ImageIO.read(new File(filePath));
			bi = removeBlank(bi, false);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\c.png"));
			bi = getEntrustPrice(bi);
			// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\qiguai\\d.png"));

			StringBuffer result = new StringBuffer();
			int width = bi.getWidth();
			int height = bi.getHeight();

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
						result.append(x + y + "1");
					} else {
						result.append(x + y + "0");
					}
				}
			}
			return result.toString();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	public static String xinTuoImageEntrustPriceRecognition(BufferedImage bi, SecurityCompany securityCompany) {
		try {
			bi = removeBlank(bi, false);
			// ImageIO.write(bi, "png", new File("C:\\test\\c.png"));
			if (securityCompany == SecurityCompany.HONGXIN_SECURITIES
					|| securityCompany == SecurityCompany.ZHONGXIN_JIANTOU
					|| securityCompany == SecurityCompany.GUANGDA_SECURITIES
					|| securityCompany == SecurityCompany.ZHONGXIN_SECURITIES
					|| securityCompany == SecurityCompany.HUAFU_SECURITIES
					|| securityCompany == SecurityCompany.TIANFENG_SECURITIES) {
				bi = getEntrustPrice(bi);
			} else if (securityCompany == SecurityCompany.WANLIAN_SECURITIES
					|| securityCompany == SecurityCompany.GUOJING_SECURITIES) {
				bi = getEntrustPriceFromRight(bi);
			}
			// ImageIO.write(bi, "png", new File("C:\\test\\d.png"));
			StringBuffer result = new StringBuffer();
			int width = bi.getWidth();
			int height = bi.getHeight();

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
						result.append(x + y + "1");
					} else {
						result.append(x + y + "0");
					}
				}
			}
			return result.toString();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	public static String xinTuoImageRiskcontrolRecognition(BufferedImage bi, SecurityCompany securityCompany) {
		try {
			long beginTime = System.currentTimeMillis();
			if (securityCompany == SecurityCompany.HONGXIN_SECURITIES
					|| securityCompany == SecurityCompany.ZHONGXIN_JIANTOU
					|| securityCompany == SecurityCompany.ZHONGXIN_SECURITIES
					|| securityCompany == SecurityCompany.ZHONGTAI_SECURITIES
					|| securityCompany == SecurityCompany.CHANGJIANG_SECURITIES
					|| securityCompany == SecurityCompany.GUOJING_SECURITIES) {
				bi = getXintuoRiskcontrol(bi);
			} else {
				return null;
			}
			StringBuffer result = new StringBuffer();
			int width = bi.getWidth();
			int height = bi.getHeight();

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isBlackForXintuo(bi.getRGB(x, y)) == 1) {
						result.append(x + "" + y + "1");
					} else {
						result.append(x + "" + y + "0");
					}
				}
			}
			logger.info("触发风控图片识别耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
			return result.toString();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	private static BufferedImage getXintuoRiskcontrol(BufferedImage image) throws IOException, FileNotFoundException,
			Exception {
		long removeBlankBeginTime = System.currentTimeMillis();
		BufferedImage bi = removeBlank(image, false);
		System.out.println("去黑边耗时：" + (System.currentTimeMillis() - removeBlankBeginTime));
		// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\b1.png"));
		long changeToBlackWhiteImageBeginTime = System.currentTimeMillis();
		bi = changeToBlackWhiteImage(bi);
		System.out.println("二值化耗时：" + (System.currentTimeMillis() - changeToBlackWhiteImageBeginTime));
		// ImageIO.write(bi, "png", new File("C:\\Users\\wei\\Desktop\\codes\\c1.png"));

		long toStringBeginTime = System.currentTimeMillis();

		int beginX = 0;

		int count = 0;
		boolean preIsAllWhite = false;
		for (int x = bi.getWidth() - 1; x > 0; x--) {
			boolean findBeginX = true;
			for (int y = 0; y < bi.getHeight(); y++) {
				if (isWhiteForXintuo(bi.getRGB(x, y)) == 0) {
					findBeginX = false;
					preIsAllWhite = false;
					break;
				}
			}
			if (findBeginX && !preIsAllWhite) {
				preIsAllWhite = true;
				count++;
			}
			if (count == 3) {
				beginX = x;
				break;
			}
		}

		bi = bi.getSubimage(beginX + 1, 0, bi.getWidth() - beginX - 1, bi.getHeight());

		System.out.println("字符串化耗时：" + (System.currentTimeMillis() - toStringBeginTime));
		return bi;
	}

}
