package com.jse.commons.util;

import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 文件操作工具类
 * 
 * @company jse-zq
 * @author Infernalzero
 * @version FileUtil.java, v 0.1 2015年12月8日 上午10:55:44
 */

public class FileUtil {
	private static final ZqLogger	logger	= new ZqLogger(FileUtil.class);

	/**
	 * 获取指定路径文件内容
	 *
	 * @param filePath
	 *            classpath相对路径
	 * @param inputStream
	 * 
	 * @return
	 */
	public static String getFileContentByInputStream(InputStream inputStream) {
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			line = reader.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = reader.readLine();
			}
			reader.close();
			inputStream.close();
		} catch (IOException e) {
			throw new ZqException("读取文件失败", e);
		}
		return sb.toString();
	}

	/**
	 * 获取property文件中对应key的值
	 *
	 * @param clazz
	 * @param key
	 * @param propertyPath
	 * @return
	 */
	public static String getContentFromPropertyByKey(Class<?> clazz, Object key, String propertyPath) {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(clazz.getResourceAsStream(propertyPath), "UTF-8"));
		} catch (IOException e) {
			throw new ZqException("Failed to load " + propertyPath, e);
		}
		return properties.get(key).toString();
	}

	/**
	 * 写入xml
	 *
	 * @param node
	 *            待写入的节点
	 * @param uri
	 *            文件路径
	 */
	public static void writeXml(Node node, String uri) {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transFactory.newTransformer();
			transformer.setOutputProperty("encoding", "UTF-8");
			transformer.setOutputProperty("indent", "yes");
			transformer.transform(new DOMSource(node), new StreamResult(uri));
		} catch (Exception e) {
			throw new ZqException("error when writing xml", e);
		}
	}

	/**
	 * 修改对应标签的内容
	 *
	 * @param tagName
	 *            标签名称
	 * @param index
	 *            索引数
	 * @param uri
	 *            文件路径
	 * @param content
	 *            修改的内容
	 */
	public static void editXmlByTagName(String tagName, int index, String uri, String content) {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
		} catch (Exception e) {
			throw new ZqException("error when reading xml", e);
		}
		Element root = document.getDocumentElement();
		Node node = root.getElementsByTagName(tagName).item(index);
		if (node == null) {
			throw new ZqException("tag not found");
		}
		node.setTextContent(content);
		writeXml(root, uri);
	}

	public static String getNodeValueByTagName(String tagName, int index, String uri) {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
			Element root = document.getDocumentElement();
			Node node = root.getElementsByTagName(tagName).item(index);
			if (node == null) {
				throw new ZqException("tag not found");
			}
			return node.getTextContent();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// public static void main(String[] args) {
	// System.out.println(FileUtil.getNodeValueByTagName("mainClass", 0, "C:/Users/wei/Desktop/test/pom.xml"));
	// System.out.println(FileUtil
	// .getNodeValueByTagName("fileNamePattern", 0, "C:/Users/wei/Desktop/test/logback.xml"));
	//
	// }
}
