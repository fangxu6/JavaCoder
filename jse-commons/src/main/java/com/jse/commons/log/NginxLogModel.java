package com.jse.commons.log;

import com.alibaba.fastjson.JSON;
import com.jse.commons.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * NGINX access main log, 对应的字段名称
 * 
 * @company jse-zq
 * @author tiebin
 * @version NginxLogModel.java, v 0.1 2017年12月4日 下午4:41:51
 */
public class NginxLogModel implements Serializable {

	private static final long	serialVersionUID	= 145402941253961654L;

	/** 本地时间 */
	private String				time_local;
	/** 服务端根据客户端访问的ip, 如果中间没有代理，则是客户端ip, 如果中间有代理，就是代理的ip, 实际上就是 访问该Nginx的客户端ip,不管几级代理 */
	private String				remote_addr;
	/** 客户端端口 */
	private String				remote_port;
	private String				remote_user;
	/** 服务端端口 */
	private String				server_port;
	/** 服务端名称 */
	private String				server_name;
	/** request请求的方法 get,post */
	private String				request_method;
	/** http 请求的第一行， 请求方法， 路径uri， 协议；如： POST /stockStat/getCommonSelectStock HTTP/1.1 */
	private String				request;
	/** 请求uri */
	private String				request_uri;
	/** 请求长度 */
	private String				request_length;
	/** http 协议scheme: http,https */
	private String				scheme;
	/** httpRefer */
	private String				http_referer;
	/** 请求中的X-Forwarded-For信息 */
	private String				http_x_forwarded_for;
	/** http的accept */
	private String				http_accept;
	/** http的accept编码格式 */
	private String				http_accept_encoding;
	/** http的accept语言 */
	private String				http_accept_language;
	/** http的cache 控制策略 */
	private String				http_cache_control;
	/** 短链接还是长连接 */
	private String				http_connection;
	private String				http_content_length;
	private String				http_cookie;
	private String				http_host;
	private String				http_pragma;
	/** 浏览器UA */
	private String				http_user_agent;
	private String				http_proxy_connection;
	/**
	 * 指的就是从接受用户请求的第一个字节到发送完响应数据的时间，即包括接收请求数据时间、程序响应时间、输出 响应数据时间
	 */
	private String				request_time;
	/** 反向代理（上游）时间 */
	private String				upstream_response_time;
	private String				sent_http_content_security_policy;
	private String				sent_http_connection;
	private String				sent_http_content_encoding;
	private String				sent_http_content_type;
	private String				sent_http_location;
	private String				status;
	private String				gzip_ratio;
	private String				body_bytes_sent;
	/** 反向代理（上游）地址端口 */
	private String				upstream_addr;

	private transient String	zhuanqianSessionId;
	private transient String	zquk;
	private transient String	realIp;

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public long getTimeLocalTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_NGINX_TIME_LOCAL, Locale.ENGLISH);
		try {
			return sdf.parse(this.getTime_local()).getTime();
		} catch (Exception e) {
			return -1L;
		}
	}

	public void setTimeLocalTime(long time) {
	}

	public String getTime_local() {
		return time_local;
	}

	public void setTime_local(String time_local) {
		this.time_local = time_local;
	}

	public String getRemote_addr() {
		return remote_addr;
	}

	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}

	public String getRemote_port() {
		return remote_port;
	}

	public void setRemote_port(String remote_port) {
		this.remote_port = remote_port;
	}

	public String getRemote_user() {
		return remote_user;
	}

	public void setRemote_user(String remote_user) {
		this.remote_user = remote_user;
	}

	public String getServer_port() {
		return server_port;
	}

	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getRequest_method() {
		return request_method;
	}

	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequest_uri() {
		return request_uri;
	}

	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}

	public String getRequest_length() {
		return request_length;
	}

	public void setRequest_length(String request_length) {
		this.request_length = request_length;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHttp_referer() {
		return http_referer;
	}

	public void setHttp_referer(String http_referer) {
		this.http_referer = http_referer;
	}

	public String getHttp_x_forwarded_for() {
		return http_x_forwarded_for;
	}

	public void setHttp_x_forwarded_for(String http_x_forwarded_for) {

		if (StringUtils.isNotBlank(http_x_forwarded_for) && !"unKnown".equalsIgnoreCase(http_x_forwarded_for)) {
			int index = http_x_forwarded_for.indexOf(",");
			if (index != -1) {
				realIp = http_x_forwarded_for.substring(0, index);
			} else {
				realIp = http_x_forwarded_for;
			}
		}

		this.http_x_forwarded_for = http_x_forwarded_for;
	}

	public String getHttp_accept() {
		return http_accept;
	}

	public void setHttp_accept(String http_accept) {
		this.http_accept = http_accept;
	}

	public String getHttp_accept_encoding() {
		return http_accept_encoding;
	}

	public void setHttp_accept_encoding(String http_accept_encoding) {
		this.http_accept_encoding = http_accept_encoding;
	}

	public String getHttp_accept_language() {
		return http_accept_language;
	}

	public void setHttp_accept_language(String http_accept_language) {
		this.http_accept_language = http_accept_language;
	}

	public String getHttp_cache_control() {
		return http_cache_control;
	}

	public void setHttp_cache_control(String http_cache_control) {
		this.http_cache_control = http_cache_control;
	}

	public String getHttp_connection() {
		return http_connection;
	}

	public void setHttp_connection(String http_connection) {
		this.http_connection = http_connection;
	}

	public String getHttp_content_length() {
		return http_content_length;
	}

	public void setHttp_content_length(String http_content_length) {
		this.http_content_length = http_content_length;
	}

	public String getHttp_cookie() {
		return http_cookie;
	}

	public void setHttp_cookie(String http_cookie) {
		this.http_cookie = http_cookie;
		String[] cookieArr = http_cookie.split(";");
		for (String string : cookieArr) {
			if (string.contains("zhuanqianSessionId")) {
				String[] sessionArr = string.split("=");
				if (sessionArr.length == 2) {
					this.zhuanqianSessionId = sessionArr[1];
				}
			} else if (string.contains("zquk")) {
				String[] zqukArr = string.split("=");
				if (zqukArr.length == 2) {
					this.zquk = zqukArr[1];
				}
			}
		}
	}

	public String getHttp_host() {
		return http_host;
	}

	public void setHttp_host(String http_host) {
		this.http_host = http_host;
	}

	public String getHttp_pragma() {
		return http_pragma;
	}

	public void setHttp_pragma(String http_pragma) {
		this.http_pragma = http_pragma;
	}

	public String getHttp_user_agent() {
		return http_user_agent;
	}

	public void setHttp_user_agent(String http_user_agent) {
		this.http_user_agent = http_user_agent;
	}

	public String getHttp_proxy_connection() {
		return http_proxy_connection;
	}

	public void setHttp_proxy_connection(String http_proxy_connection) {
		this.http_proxy_connection = http_proxy_connection;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getUpstream_response_time() {
		return upstream_response_time;
	}

	public void setUpstream_response_time(String upstream_response_time) {
		this.upstream_response_time = upstream_response_time;
	}

	public String getSent_http_content_security_policy() {
		return sent_http_content_security_policy;
	}

	public void setSent_http_content_security_policy(String sent_http_content_security_policy) {
		this.sent_http_content_security_policy = sent_http_content_security_policy;
	}

	public String getSent_http_connection() {
		return sent_http_connection;
	}

	public void setSent_http_connection(String sent_http_connection) {
		this.sent_http_connection = sent_http_connection;
	}

	public String getSent_http_content_encoding() {
		return sent_http_content_encoding;
	}

	public void setSent_http_content_encoding(String sent_http_content_encoding) {
		this.sent_http_content_encoding = sent_http_content_encoding;
	}

	public String getSent_http_content_type() {
		return sent_http_content_type;
	}

	public void setSent_http_content_type(String sent_http_content_type) {
		this.sent_http_content_type = sent_http_content_type;
	}

	public String getSent_http_location() {
		return sent_http_location;
	}

	public void setSent_http_location(String sent_http_location) {
		this.sent_http_location = sent_http_location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGzip_ratio() {
		return gzip_ratio;
	}

	public void setGzip_ratio(String gzip_ratio) {
		this.gzip_ratio = gzip_ratio;
	}

	public String getBody_bytes_sent() {
		return body_bytes_sent;
	}

	public void setBody_bytes_sent(String body_bytes_sent) {
		this.body_bytes_sent = body_bytes_sent;
	}

	public String getUpstream_addr() {
		return upstream_addr;
	}

	public void setUpstream_addr(String upstream_addr) {
		this.upstream_addr = upstream_addr;
	}

	public void setZhuanqianSessionId(String zhuanqianSessionId) {
	}

	public void setZquk(String zquk) {
	}

	public String getZhuanqianSessionId() {
		return zhuanqianSessionId;
	}

	public String getZquk() {
		return zquk;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
