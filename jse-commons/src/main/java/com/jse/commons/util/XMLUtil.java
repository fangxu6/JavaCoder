package com.jse.commons.util;

import com.zhuanqian.commons.enums.SecurityCompany;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @company jse-zq
 * @author tiebin
 * @version XMLUtil.java, v 0.1 2017年8月18日 下午9:49:29
 */
public final class XMLUtil {

	private XMLUtil() {
	}

	private static final String	insert	= "INSERT INTO `stock`.`BrokerServer` (  `securityCompanyId`, `securityCompany`, `businessAccountType`, `brokerServerHost`, `brokerServerPort`, `proxyServerPort`, `tdxVersion`, `createTime`, `updateTime`, `deleteTime`, `reserve0`, `reserve1`, `reserve2`, `isValid`, `isEnabled`, `isMarginTradingHost`) "
												+ "VALUES ( '#securityId#', '#securityName#', '#bizType#', '#ip#', '#port#', '#proxyPort#', '#version#', '1481598534196', '1503049985145', NULL, NULL, NULL, NULL, '0', '0', '0');";

	public static void etradeLoad(String location, SecurityCompany sc) throws Exception {

		SAXReader reader = new SAXReader();
		reader.setEncoding("gb2312");
		Document doc = reader.read(location);

		@SuppressWarnings("unchecked")
		List<Element> elementList = doc.selectNodes("ProfileOfSystem/Classification/ITEM/Site/ITEM");
		int i = 16920;
		List<String> ipList = new ArrayList<String>();
		for (Element element : elementList) {
			String insertSQL = insert.replace("#securityId#", "" + sc.id()).replace("#securityName#", sc.getName())
					.replace("#bizType#", "1").replace("#ip#", element.attributeValue("Address"))
					.replace("#port#", element.attributeValue("Port")).replace("#proxyPort#", "" + i)
					.replace("#version#", "6");
			i++;
			System.out.println(insertSQL);
			ipList.add(element.attributeValue("Address") + ":" + element.attributeValue("Port"));
		}
		StringBuffer sb = new StringBuffer();
		for (String string : ipList) {
			sb.append(string).append(",");
		}

		if (sb.length() > 0) {
			System.out.println(sb.substring(0, sb.length() - 1));
		}
	}

	private static HashMap<String, String>	BASIC_AUTH_HEADER	= new HashMap<String, String>();

	public static void main(String[] args) throws Exception {
		// XMLUtil.etradeLoad("C:\\sws2010\\eTrade.xml", SecurityCompany.SHENYIN_WANGUO);

		String minuteUurl = "http://localhost:8081/ib-agent/market/histroyNewHeartBeat";
		Map<String, String> minuteParamMap = new HashMap<String, String>();
		minuteParamMap.put("symbol", "USD");
		minuteParamMap.put("exCountryCode", "3");
		String rspString = ZqHttpClientUtil.executePost(minuteUurl, BASIC_AUTH_HEADER, minuteParamMap, 3000000);
		System.out.println(rspString);
		// if (!isMinuteNormal) {
		// // logger.error("分时检测，agentServer: " + agentServer + ":检测结果:fail");
		// }

	}
}
