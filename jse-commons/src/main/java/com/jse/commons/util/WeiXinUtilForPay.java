package com.jse.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 微信通知util，支付单独使用（与之前区分原因，接口参数有变化）
 * 
 * @company jse-zq
 * @author LuoJiang
 * @version WeiXinUtilForPay.java, v 0.1 2018年9月5日 下午7:28:30
 */
public class WeiXinUtilForPay {
	private static final ZqLogger	logger					= new ZqLogger(WeiXinUtilForPay.class);

	// 企业id
	private static final String		CORPID					= "ww1f2184e6ea47e2d7";
	// 自建应用秘钥
	private static final String		SELF_CONSTRUCT_APPKEY	= "0EVtVttazBw1KOdomPK7JfoVdPowFcrQ6033Gdgd9XM";

	// 创建会话
	private static final String		CREATE_CHAT_URL			= "https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=";
	// 修改会话
	private static final String		CHANGE_CHAT_URL			= "https://qyapi.weixin.qq.com/cgi-bin/appchat/update?access_token=";
	// 修改会话
	private static final String		QUERY_CHAT_URL			= "https://qyapi.weixin.qq.com/cgi-bin/appchat/get?access_token=";

	private static final String		SEND_MSG_URL			= "https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token=";
	// 获取凭证
	private static final String		GET_TOKEN_URL			= "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

	public enum ChatGroupEnum {
		alipayChatGroup("支付宝流水监控群", "10001"),
		testChatGroup("测试消息群", "10002");
		private String	groupName;
		private String	groupId;

		public String getGroupName() {
			return groupName;
		}

		public String getGroupId() {
			return groupId;
		}

		private ChatGroupEnum(String groupName, String groupId) {
			this.groupName = groupName;
			this.groupId = groupId;
		}

	}

	/**
	 * 发送消息
	 *
	 * @param content
	 *            发送正文
	 * @param groupId
	 *            发送消息群
	 */
	public static void sendMessage(String content, ChatGroupEnum group) {
		String accessToken = getAccessToken(CORPID, SELF_CONSTRUCT_APPKEY);
		JSONObject json = new JSONObject();
		json.put("chatid", group.getGroupId());
		json.put("msgtype", "text");
		JSONObject textJson = new JSONObject();
		textJson.put("content", content);
		json.put("text", textJson);
		try {
			ZqHttpClientUtil.postJSON(SEND_MSG_URL + accessToken, null, json, 30000);
		} catch (Exception e) {
			logger.error("发送微信消息失败", e);
			throw new ZqException("发送微信失败", e);
		}
	}

	public static void createChatGroup(String chatId, String groupName, String owner, String... memberList) {
		List<String> userList = new ArrayList<String>();
		if (memberList.length == 0) {
			throw new ZqException("组内成员不能为空");
		}
		for (String member : memberList) {
			userList.add(member);
		}
		String accessToken = getAccessToken(CORPID, SELF_CONSTRUCT_APPKEY);
		JSONObject json = new JSONObject();
		json.put("chatid", chatId);
		json.put("name", groupName);
		json.put("owner", owner);
		json.put("userlist", userList);
		try {
			String res = ZqHttpClientUtil.postJSON(CREATE_CHAT_URL + accessToken, null, json, 30000);
			logger.info("微信创建会话返回结果：" + res);
		} catch (Exception e) {
			logger.error("创建会话失败", e);
			throw new ZqException("创建会话失败", e);
		}
	}

	public static void changeChatGroup(String chatId, String groupName, String owner, List<String> addUserList,
			List<String> deleUserList) {
		if (StringUtils.isEmpty(chatId)) {
			throw new ZqException("chatid 不可为空");
		}
		String accessToken = getAccessToken(CORPID, SELF_CONSTRUCT_APPKEY);
		JSONObject json = new JSONObject();
		json.put("chatid", chatId);
		json.put("name", groupName);
		json.put("owner", owner);
		json.put("add_user_list", addUserList);
		json.put("del_user_list", deleUserList);
		try {
			String res = ZqHttpClientUtil.postJSON(CHANGE_CHAT_URL + accessToken, null, json, 30000);
			logger.info("微信修改会话返回结果：" + res);
		} catch (Exception e) {
			logger.error("修改会话失败", e);
			throw new ZqException("修改会话失败", e);
		}
	}

	public static void queryChatGroup(String chatId) {
		if (StringUtils.isEmpty(chatId)) {
			throw new ZqException("chatid 不可为空");
		}
		String accessToken = getAccessToken(CORPID, SELF_CONSTRUCT_APPKEY);
		try {
			String res = ZqHttpClientUtil.executeGet(QUERY_CHAT_URL + accessToken + "&chatid=" + chatId);
			logger.info("返回结果：" + res);
		} catch (Exception e) {
			logger.error("修改会话失败", e);
			throw new ZqException("修改会话失败", e);
		}
	}

	private static String getAccessToken(String corpId, String corpSecret) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("corpid", corpId);
		paramMap.put("corpsecret", corpSecret);
		String response = ZqHttpClientUtil.executeGet(GET_TOKEN_URL, paramMap);
		JSONObject responseJSON = JSON.parseObject(response);
		return responseJSON.get("access_token") == null ? null : responseJSON.get("access_token").toString();
	}

	public static void main(String[] args) {
		// sendMessage("收到吗~~~~有人吗", "10002");
		// createChatGroup("10002", "在测试一下群", "kaiwen", "kaiwen", "luojiang", "zilv");
		// List<String> addList = new ArrayList<String>();
		// addList.add("ARen");
		// addList.add("gaara");
		// changeChatGroup("10001", "支付宝流水监控", "luojiang", addList, new ArrayList<String>());
		queryChatGroup("10001");
	}
}
