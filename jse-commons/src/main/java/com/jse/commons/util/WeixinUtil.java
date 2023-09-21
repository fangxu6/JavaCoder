package com.jse.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuanqian.commons.cache.CacheOperator;
import com.zhuanqian.commons.cache.RedisDB;
import com.zhuanqian.commons.constant.Constant;
import com.zhuanqian.commons.content.WeixinDepartment;
import com.zhuanqian.commons.enums.WeixinMessageTemplate;
import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;
import com.zhuanqian.commons.spring.SpringContextHolder;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;

/**
 * 通过微信接口发送消息
 * 
 * @company jse-zq
 * @author Infernalzero
 * @version WeixinUtil.java, v 0.1 2015年8月3日 下午4:32:01
 */

public class WeixinUtil {
	private static final ZqLogger		logger								= new ZqLogger(WeixinUtil.class);
	/* 微信企业号 */
	private static final String			CORPID								= "wx53bc877f1a045312";
	/* 微信企业号秘钥 */
	private static final String			CORP_SECRET							= "fvy7OJnVD7avRa01wkf9fMdtSujnqqhfQOWCUrY2ocbqG_BfNfrZEVAXZx6hBoQC";
	private static final String			CHAT_SEND_URL						= "https://qyapi.weixin.qq.com/cgi-bin/chat/send?access_token=";
	/* 微信获取token URL */
	private static final String			GET_TOKEN_URL						= "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	/* 微信创建会话 URL */
	private static final String			CHAT_CREATE_URL						= "https://qyapi.weixin.qq.com/cgi-bin/chat/create?access_token=";
	/* 微信创建成员 URL */
	private static final String			USER_CREATE_URL						= "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=";
	/* 微信修改成员 URL */
	private static final String			CHAT_UPDATE_URL						= "https://qyapi.weixin.qq.com/cgi-bin/chat/update?access_token=";
	/* 创建部门 URL */
	private static final String			DEPARTMENT_CREATE_URL				= "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=";
	/* 获取部门 URL */
	private static final String			DEPARTMENT_QUERY_URL				= "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=";
	/* 资管助手发送消息应用名称 */
	private static final String			DISPATCHER							= "tongzhizhongxin";
	/* 资管助手微信企业号 */
	private static final String			ASSET_MANAGEMENT_CORP_ID			= "wx705b4177f978c183";
	/* 资管助手微信企业号秘钥 */
	private static final String			ASSET_MANAGEMENT_CORP_SECRET		= "Nt1oL4WTtsDW5m9OOJWRCq7iwpTwfu4Fe90gqDiTMon3KX_QqT_7ItDOw5dHKDhh";
	private static long					minInterval							= 60000;
	private static Map<String, Long>	lastSendTimeInMillisMap				= new ConcurrentHashMap<String, Long>(64);

	private static String				areaAndHostInfo						= null;

	/* 企业微信创建群聊 URL */
	private static final String			ENWECHAT_CHATGROUP_CREATE_URL		= "https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=";
	/* 企业微信更新群聊信息 URL */
	private static final String			ENWECHAT_CHATGROUP_UPDATE_URL		= "https://qyapi.weixin.qq.com/cgi-bin/appchat/update?access_token=";
	/* 企业微信发送消息到群聊 URL */
	private static final String			ENWECHAT_CHATGROUP_SENDMESSAGE_URL	= "https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token=";
	/* 企业微信获取token的URL */
	private static final String			ENWECHAT_TOKEN_URL					= "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	private static Map<String, Long>	errorMsg							= new HashMap<String, Long>();

	public static String getAreaAndHostInfo() {
		if (WeixinUtil.areaAndHostInfo == null) {
			synchronized (WeixinUtil.class) {
				if (WeixinUtil.areaAndHostInfo == null) {
					init();
				}
			}
		}
		return WeixinUtil.areaAndHostInfo;
	}

	public static void setAreaAndHostInfo(String areaAndHostInfo) {
		WeixinUtil.areaAndHostInfo = areaAndHostInfo;
	}

	private static void init() {
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName().toString();
		} catch (UnknownHostException e) {
			hostName = "未知主机";
		}// 获得本机名称
		String areaName = SpringContextHolder.getProperty("area.name");
		if (StringUtils.isBlank(areaName)) {
			areaName = "未知区域";
		}
		setAreaAndHostInfo(areaName + "(" + hostName + "):\n");
	}

	/**
	 * 发送消息给所有人(系统报警)
	 *
	 * @param message
	 * @param groupId
	 *            分组名称 @see com.zhuanqian.commons.constant.Constant 选择SYSTEM_WARNING_GROUP或BUSSINESS_WARNING_GROUP
	 * @throws UnsupportedEncodingException
	 */
	public static void sendTextMessageToAll(String msg, final String groupId) {

		final String message = getAreaAndHostInfo() + msg;

		// FIXME 后续该部分代码需要发送报警控制策略里面，暂时先放在这边
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) SpringContextHolder
				.getBean("sendWarningThreadPool");
		try {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {

					long currentTime = System.currentTimeMillis();
					Long lastSendTime = lastSendTimeInMillisMap.get(message + "-group:" + groupId);
					if (lastSendTime != null && currentTime - lastSendTime < minInterval) {
						return;
					}
					String accessToken = getAccessToken(CORPID, CORP_SECRET);
					HashMap<String, String> receiverMap = new HashMap<String, String>();
					HashMap<String, String> textMap = new HashMap<String, String>();
					JSONObject json = new JSONObject();
					textMap.put("content", message);
					receiverMap.put("id", groupId);
					receiverMap.put("type", "group");
					json.put("receiver", receiverMap);
					json.put("sender", "baojingzhongxin");
					json.put("msgtype", "text");
					json.put("text", textMap);
					try {
						ZqHttpClientUtil.postJSON(CHAT_SEND_URL + accessToken, null, json, 30000);
					} catch (Exception e) {
						logger.error(e, "333");
						throw new ZqException("发送微信失败", e);
					}
					lastSendTimeInMillisMap.put(message + "-group:" + groupId, currentTime);
				}
			});
		} catch (RejectedExecutionException e) {
			logger.error("任务过量拒绝任务", e);
		} catch (Throwable t) {
			logger.error("发送微信任务失败", t);
		}
	}

	/**
	 * 发送给机构的信息,和内部报警区分开
	 *
	 * @param message
	 * @param groupId
	 */
	public static void sendMessageToOrganization(final String message, final String groupId) {
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) SpringContextHolder
				.getBean("sendWarningThreadPool");
		try {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					String accessToken = getAccessToken(ASSET_MANAGEMENT_CORP_ID, ASSET_MANAGEMENT_CORP_SECRET);
					HashMap<String, String> receiverMap = new HashMap<String, String>();
					HashMap<String, String> textMap = new HashMap<String, String>();
					JSONObject json = new JSONObject();
					textMap.put("content", message);
					receiverMap.put("id", groupId);
					receiverMap.put("type", "group");
					json.put("receiver", receiverMap);
					json.put("sender", DISPATCHER);
					json.put("msgtype", "text");
					json.put("text", textMap);
					try {
						ZqHttpClientUtil.postJSON(CHAT_SEND_URL + accessToken, null, json, 30000);
					} catch (Exception e) {
						throw new ZqException("发送微信失败", e);
					}
				}
			});
		} catch (RejectedExecutionException e) {
			logger.error("sendMessageToOrganization任务过量拒绝任务", e);
		} catch (Throwable t) {
			logger.error("sendMessageToOrganization发送微信任务失败", t);
		}
	}

	/**
	 * 创建会话
	 *
	 * @param groupId
	 * @param groupName
	 * @param owner
	 * @param userList
	 */
	public static String createChat(final int groupId, final String groupName, final String owner, List<String> userList) {
		if (StringUtils.isBlank(owner)) {
			throw new ZqException("会话创建人不能为空");
		}
		if (CollectionUtils.isEmpty(userList)) {
			throw new ZqException("成员列表不能为空");
		}
		String accessToken = getAccessToken(ASSET_MANAGEMENT_CORP_ID, ASSET_MANAGEMENT_CORP_SECRET);
		JSONObject json = new JSONObject();
		userList.add(DISPATCHER);
		userList.add("baojingzhongxin");
		userList.add("jiwan");
		json.put("chatid", groupId);
		json.put("name", groupName);
		json.put("owner", owner);
		json.put("userlist", userList);
		try {
			return ZqHttpClientUtil.postJSON(CHAT_CREATE_URL + accessToken, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("创建会话失败", e);
		}
	}

	/**
	 * 创建部门
	 *
	 * @param departmentName
	 *            部门名称,必填
	 * @param parentId
	 *            父部门的id,根部门ID @see com.zhuanqian.commons.constant.Constant
	 * @param departmentId
	 *            若不填则随机生成
	 */
	public static String createDepartment(final String departmentName, final int parentId, final Integer departmentId) {
		if (StringUtils.isBlank(departmentName)) {
			throw new ZqException("部门名称不能为空");
		}
		String accessToken = getAccessToken(ASSET_MANAGEMENT_CORP_ID, ASSET_MANAGEMENT_CORP_SECRET);
		JSONObject json = new JSONObject();
		json.put("name", departmentName);
		json.put("parentid", parentId);
		if (departmentId != null) {
			json.put("id", departmentId);
		}
		try {
			return ZqHttpClientUtil.postJSON(DEPARTMENT_CREATE_URL + accessToken, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("创建部门失败", e);
		}
	}

	/**
	 * 创建成员
	 *
	 * @param userId
	 *            成员UserID。对应管理端的帐号,企业内必须唯一。长度为1~64个字节,必填
	 * @param userName
	 *            成员名称。长度为1~64个字节,必填
	 * @param departmentList
	 *            成员所属部门id列表,必填
	 * @param mobile
	 *            手机号码。企业内必须唯一,必填
	 */
	public static String createUser(final String userId, final String userName, List<String> departmentList,
			String mobile) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(userName) || CollectionUtils.isEmpty(departmentList)) {
			throw new ZqException("必填参数不能为空");
		}
		if (StringUtils.isBlank(mobile)) {
			mobile = userId + "000";
		}
		String accessToken = getAccessToken(ASSET_MANAGEMENT_CORP_ID, ASSET_MANAGEMENT_CORP_SECRET);
		JSONObject json = new JSONObject();
		json.put("userid", userId);
		json.put("name", userName);
		json.put("department", departmentList);
		json.put("mobile", mobile);
		try {
			return ZqHttpClientUtil.postJSON(USER_CREATE_URL + accessToken, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("创建成员失败", e);
		}
	}

	/**
	 * 添加会话成员
	 *
	 * @param chatId
	 *            会话ID，必填
	 * @param addUserList
	 *            添加的成员
	 * @return
	 */
	public static String addUserForChat(final String chatId, String... addUserList) {
		if (StringUtils.isBlank(chatId)) {
			throw new ZqException("必填参数不能为空");
		}
		String accessToken = getAccessToken(ASSET_MANAGEMENT_CORP_ID, ASSET_MANAGEMENT_CORP_SECRET);
		JSONObject json = new JSONObject();
		json.put("chatid", chatId);
		json.put("op_user", DISPATCHER);
		json.put("add_user_list", addUserList);
		try {
			return ZqHttpClientUtil.postJSON(CHAT_UPDATE_URL + accessToken, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("添加成员失败", e);
		}
	}

	/**
	 * 获取token
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getAccessToken(String corpId, String corpSecret) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("corpid", corpId);
		paramMap.put("corpsecret", corpSecret);
		String response = ZqHttpClientUtil.executeGet(GET_TOKEN_URL, paramMap);
		HashMap<String, String> responseMap = JSON.parseObject(response, HashMap.class);
		return responseMap.get("access_token");
	}

	/**
	 * 新建一个(报警)群
	 * 
	 * @param chatId会话id
	 * @param name群名称
	 * @param owner管理员userid
	 *            ，必须是该会话userlist的成员之一
	 * @param userList会话成员列表
	 *            ，成员用userid来标识。会话成员必须在3人或以上，1000人以下
	 * @return
	 */
	public static String createNewChatGroup(final int chatId, final String name, final String owner,
			List<String> userList) {
		if (StringUtils.isBlank(owner)) {
			throw new ZqException("会话创建人不能为空");
		}
		if (CollectionUtils.isEmpty(userList)) {
			throw new ZqException("成员列表不能为空");
		}
		String accessToken = getAccessToken(CORPID, CORP_SECRET);
		// String accessToken = getAccessToken("wx28e9728c5acefdaa",
		// "6CeJZpVHCLNEZBkPGawdgpYxicLsOWKI9CzH6YCzefxQy_bdg_9vIQk-0vzzmtOY");
		JSONObject json = new JSONObject();
		json.put("chatid", chatId);
		json.put("owner", owner);
		json.put("name", name);
		json.put("userlist", userList);
		try {
			return ZqHttpClientUtil.postJSON("https://qyapi.weixin.qq.com/cgi-bin/chat/create?access_token="
					+ accessToken, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("添加会话群失败", e);
		}
	}

	/**
	 * 获取部门列表
	 *
	 * @param id
	 *            部门id ,获取指定部门及其下的子部门
	 * @return
	 */
	public static List<WeixinDepartment> getDepartmentList(String id) {
		String accessToken = getAccessToken(ASSET_MANAGEMENT_CORP_ID, ASSET_MANAGEMENT_CORP_SECRET);
		String url = DEPARTMENT_QUERY_URL + accessToken;
		if (StringUtils.isNotBlank(id)) {
			url = url + "&id=" + id;
		}
		String response = ZqHttpClientUtil.executeGet(url);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> responseMap = JSON.parseObject(response, HashMap.class);
		String errmsg = responseMap.get("errmsg") + "";
		List<WeixinDepartment> weixinDepartmentList = null;
		if (StringUtils.equalsIgnoreCase("ok", errmsg)) {
			String departments = responseMap.get("department") + "";
			weixinDepartmentList = JSONObject.parseArray(departments, WeixinDepartment.class);
		}
		return weixinDepartmentList;
	}

	/**
	 * 参数不能为空
	 */
	private static final void requireNotBlank(String... list) {
		for (String str : list) {
			if (StringUtils.isBlank(str)) {
				throw new NullPointerException();
			}
		}
	}

	/**
	 * 获取token
	 *
	 * @param getTokenUrl
	 *            --企业微信获取token的URL
	 * @param corpId
	 *            --企业id(企业信息页面最下方的企业ID)
	 * @param corpSecret
	 *            --企业应用的Secret字段
	 */
	@SuppressWarnings("unchecked")
	public static String getToken(String getTokenUrl, String corpId, String corpSecret) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("corpid", corpId);
		paramMap.put("corpsecret", corpSecret);
		String response = ZqHttpClientUtil.executeGet(getTokenUrl, paramMap);
		HashMap<String, String> responseMap = JSON.parseObject(response, HashMap.class);
		String token = responseMap.get("access_token");
		if (StringUtils.isBlank(token)) {
			throw new ZqException("获取token失败");
		}
		return token;
	}

	/**
	 * 新建一个(报警)群
	 * 
	 * @param createChatGroupUrl
	 *            --企业微信创建群聊的url
	 * @param token
	 *            --token
	 * @param chatId
	 *            --群聊id
	 * @param groupName
	 *            --群名称,最多50个utf8字符，超过将截断
	 * @param groupManager
	 *            --群主(群管理),必须是该会话groupMemberList的成员之一
	 * @param groupMemberList
	 *            --群成员列表 ,至少两个人
	 * @return 创建成功的返回值{ "errcode" : 0, "errmsg" : "ok", "chatid" : "CHATID" }
	 */
	public static String createChatGroup(String createChatGroupUrl, String token, final String chatId,
			final String groupName, final String groupManager, List<String> groupMemberList) {
		requireNotBlank(createChatGroupUrl, token, chatId, groupName, groupManager);
		if (CollectionUtils.isEmpty(groupMemberList)) {
			throw new ZqException("群成员列表不能为空");
		}
		if (!groupMemberList.contains(groupManager)) {
			groupMemberList.add(groupManager);
		}
		JSONObject json = new JSONObject();
		json.put("name", groupName);
		json.put("owner", groupManager);
		json.put("userlist", groupMemberList);
		json.put("chatid", chatId);
		try {
			return ZqHttpClientUtil.postJSON(createChatGroupUrl + token, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("创建群聊失败", e);
		}
	}

	/**
	 * 更新群聊信息
	 * 
	 * @param updateChatGroupUrl
	 *            --企业微信更新群聊 URL
	 * @param token
	 *            --token
	 * @param chatId
	 *            --群聊的唯一id,只允许字符0-9及字母a-zA-Z,最长32个字符
	 * @param newGroupName
	 *            --将与chatid相同的群聊名改为newGroupName
	 * @param newGroupManager
	 *            --将与chatid相同的群聊的群主改为newGroupManager
	 * @param addUserList
	 *            --需要添加的成员id
	 * @param delUserList
	 *            --需要移除的成员id
	 * @return 修改成功的返回值{ "errcode" : 0, "errmsg" : "ok" }
	 */
	public static String updateChatGroup(String updateChatGroupUrl, String token, final String chatId,
			final String newGroupName, final String newGroupManager, List<String> addUserList, List<String> delUserList) {
		requireNotBlank(updateChatGroupUrl, token, chatId);
		JSONObject json = new JSONObject();
		json.put("chatid", chatId);
		json.put("name", newGroupName);
		json.put("owner", newGroupManager);
		json.put("add_user_list", addUserList);
		json.put("del_user_list", delUserList);
		try {
			return ZqHttpClientUtil.postJSON(updateChatGroupUrl + token, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("修改群聊失败", e);
		}
	}

	/**
	 * 推送文本消息(报警)
	 * 
	 * @limit copy from https://open.work.weixin.qq.com/api/old/doc#13294 ---只允许企业自建应用调用，且应用的可见范围必须是根部门； ---chatid所代表的群必须是该应用所创建；
	 *        ---每企业消息发送量不可超过2万人次/分，不可超过20万人次/小时（若群有100人，每发一次消息算100人次）； ---每个成员在群中收到的应用消息不可超过200条/分，1万条/天，超过会被丢弃（接口不会报错）；
	 * @param sendMessageUrl
	 *            --企业微信推送消息的URL
	 * @param token
	 *            --token
	 * @param chatId
	 *            --群聊的唯一id,只允许字符0-9及字母a-zA-Z,最长32个字符
	 * @param message
	 *            --需要推送的消息(utf-8编码)
	 * @param safe
	 *            --是否是保密消息,true是,false不是
	 */
	public static String sendTextMessage(String sendMessageUrl, String token, final String chatId, String message,
			boolean safe) {
		requireNotBlank(sendMessageUrl, token, chatId, message);
		JSONObject json = new JSONObject();
		json.put("chatid", chatId);
		json.put("msgtype", "text");
		HashMap<String, String> map = new HashMap<String, String>(1);
		map.put("content", message);
		json.put("text", map);
		if (BooleanUtils.isFalse(safe)) {
			json.put("safe", 1);
		} else {
			json.put("safe", 0);
		}
		logger.info("pushMessage:[" + json.toString() + "]");
		try {
			return ZqHttpClientUtil.postJSON(sendMessageUrl + token, null, json, 30000);
		} catch (Exception e) {
			throw new ZqException("推送消息:" + message + "失败", e);
		}
	}

	/**
	 * 为企业创建群聊
	 */
	public static String createChatGroup(Corp corp, final String chatId, final String groupName,
			final String groupManager, List<String> groupMemberList) {
		String token = getToken(ENWECHAT_TOKEN_URL, corp.getCorpId(), corp.getSecret());
		createChatGroup(ENWECHAT_CHATGROUP_CREATE_URL, token, chatId, groupName, groupManager, groupMemberList);
		return sendTextMessage(ENWECHAT_CHATGROUP_SENDMESSAGE_URL, token, chatId, "CHATID:" + chatId, true);
	}

	/**
	 * 修改企业群聊信息
	 */
	public static String updateChatGroup(Corp corp, final String chatId, final String newGroupName,
			final String newGroupManager, List<String> addUserList, List<String> delUserList) {
		String token = getToken(ENWECHAT_TOKEN_URL, corp.getCorpId(), corp.getSecret());
		return updateChatGroup(ENWECHAT_CHATGROUP_UPDATE_URL, token, chatId, newGroupName, newGroupManager,
				addUserList, delUserList);
	}

	/**
	 * 发送消息到企业微信群聊
	 */
	public static String sendTextMessage(Corp corp, final String chatId, String message) {
		String token = getToken(ENWECHAT_TOKEN_URL, corp.getCorpId(), corp.getSecret());
		return sendTextMessage(ENWECHAT_CHATGROUP_SENDMESSAGE_URL, token, chatId, message, true);
	}

	/**
	 * 
	 * @param corpId
	 *            企业id
	 * @param msg
	 *            需要发送的消息
	 * @param groupId
	 *            群组id
	 */
	public static void sendTextMessageToAll(final Corp corp, String msg, final String groupId) {
		if (corp == Corp.NIUQIA) {
			sendTextMessageToAll(msg, groupId);
			return;
		}
		final String message = getAreaAndHostInfo() + msg;
		// FIXME 后续该部分代码需要发送报警控制策略里面，暂时先放在这边
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) SpringContextHolder
				.getBean("sendWarningThreadPool");
		try {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					long currentTime = System.currentTimeMillis();
					Long lastSendTime = lastSendTimeInMillisMap.get(message + "-group:" + groupId);
					if (lastSendTime != null && currentTime - lastSendTime < minInterval) {
						return;
					}
					sendTextMessage(corp, groupId, message);
					lastSendTimeInMillisMap.put(message + "-group:" + groupId, currentTime);
				}
			});
		} catch (RejectedExecutionException e) {
			logger.error("任务过量拒绝任务", e);
		} catch (Throwable t) {
			logger.error("发送微信任务失败", t);
		}
	}

	/**
	 * 按一定的频率发送错误信息，当前时间方法内封装，相同的文案间隔1小时左右发送一次
	 */
	public static void sendMessageByFixedFrequency(String message) {
		try {
			Long latestTime = errorMsg.get(message);
			long nowTime = System.currentTimeMillis();
			long keepTime = 60L * 60L * 1000L;
			if (latestTime == null || nowTime - latestTime.longValue() >= keepTime) {
				errorMsg.put(message, nowTime);
				sendTextMessageToAll(
						Corp.QIANNIU,
						message + ",当前时间:"
								+ DateUtil.getFormatDate(new Date(), DateUtil.DATE_FORMAT_EN_B_YYYYMMDDHHMMSS),
						Constant.QIANNIU_TRADE_GROUP);
				return;
			}
			Iterator<Map.Entry<String, Long>> it = errorMsg.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Long> entry = it.next();
				latestTime = entry.getValue();
				if (latestTime == null || nowTime - latestTime.longValue() >= keepTime) {
					it.remove();
				}
			}
		} catch (Throwable t) {
			logger.error(t, ",sendWeixinMessage Error");
		}
	}

	/**
	 * 发送微信，控制时间间隔报送，方法内同一封装当前时间(yyyy-MM-dd hh:mm:ss)<br>
	 * 
	 * @param messageTemplate
	 *            微信模板
	 * @param variables
	 *            微信内容数组
	 * @param corp
	 *            企业id
	 * @param groupId
	 *            群组id
	 */
	public static void sendMessage(WeixinMessageTemplate messageTemplate, String[] variables, final Corp corp,
			final String groupId) {
		try {
			/**
			 * 示例：<br>
			 * message："资金账号:银河证券-融资融券-220189991960,委托记录入库报错,委托入库脚本报警,当前时间：2019-09-06 14:47:54"<br>
			 * 
			 * messageTemplate=WeixinMessageTemplate.SECURITIES_ENTRUSTRECORD_INTO_DB_ERROR<br>
			 * variables=new String[]{"银河证券","融资融券","220189991960","委托入库脚本报警"};<br>
			 * corp=Corp.QIANNIU<br>
			 * groupId=Constant.QIANNIU_TRADE_GROUP<br>
			 * redisKey=Corp.QIANNIU#Constant.QIANNIU_TRADE_GROUP#资金账号:银河证券-融资融券-220189991960,委托记录入库报错<br>
			 * messageGroup="资金账号:银河证券-融资融券-220189991960,委托记录入库报错"<br>
			 */
			if (messageTemplate == null || ArrayUtils.isEmpty(variables) || corp == null
					|| StringUtils.isBlank(groupId)) {
				throw new ZqException("参数错误，微信报送失败");
			}
			String[] messageArr = getInfoByWeixinMessageTemplate(messageTemplate, variables);
			String messageGroup = messageArr[0];
			String message = messageArr[1];
			long interval = messageTemplate.getIntervalSeconds();
			StringBuffer sb = new StringBuffer();
			String redisKey = sb.append(corp).append("#").append(groupId).append("#").append(messageGroup).toString();
			CacheOperator cacheOperator = SpringContextHolder.getBean(CacheOperator.class);
			RedisDB db = RedisDB.OTHER;
			// 从redis中获取数据，若还有数据存在，则说明距离上次报送时间还未到时间间隔要求
			Long lastInterval = (Long) cacheOperator.get(redisKey, db);
			logger.info("sendMessage,messageArr:" + Arrays.toString(messageArr) + ",corp:" + corp + ",groupId:"
					+ groupId + ",lastInterval:" + lastInterval);
			if (lastInterval != null) {
				return;
			}
			sb.setLength(0);
			sendTextMessageToAll(
					corp,
					sb.append(message).append(",当前时间:")
							.append(DateUtil.getFormatDate(new Date(), DateUtil.DATE_FORMAT_EN_B_YYYYMMDDHHMMSS))
							.toString(), groupId);
			if (interval > 0L) {
				cacheOperator.setEx(redisKey, interval, interval, db);
			}
		} catch (Throwable t) {
			logger.error(t, "sendMessage,messageTemplate:", JSONObject.toJSONString(messageTemplate), ",corp:", corp,
					",groupId:", groupId);
		}
	}

	/**
	 * 根据选择的模板和入参，返回{微信内容归属维度，微信内容}<br>
	 * 消息模板和消息分组模板的变量格式示例“{X}”，X从0开始按顺序取自然数值，如{0}{1}{2}....<br>
	 */
	private static String[] getInfoByWeixinMessageTemplate(WeixinMessageTemplate messageTemplate, Object[] variables) {
		String messageGroup = MessageFormat.format(messageTemplate.getMessageGroup(), variables);
		String message = MessageFormat.format(messageTemplate.getMessage(), variables);
		return new String[] { messageGroup, message };
	}

}
