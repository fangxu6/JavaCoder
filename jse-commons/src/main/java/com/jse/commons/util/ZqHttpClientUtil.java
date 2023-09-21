package com.jse.commons.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.exception.ZqHttpTimeOutException;
import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @company jse-zq
 * @author 铁镔
 * @version ZqHttpClientUtil.java, v 0.1 2015年6月1日 上午11:02:08
 */
public class ZqHttpClientUtil {
	private static final ZqLogger	logger	= new ZqLogger(ZqHttpClientUtil.class);

	/**
	 * 不需要header, 默认超时10秒
	 * 
	 * @return
	 */
	public static String executePost(String url, Map<String, String> paramMap) {
		return executePost(url, null, paramMap, 10000);
	}

	public static String executePostReturnLocation(String url, Map<String, String> headerMap,
			Map<String, String> paramMap) {
		Integer timeout = 10000;
		HttpPost httpPost = new HttpPost(url);
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		// 添加参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), StringUtils.trimToEmpty(entry.getValue())));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeout != null && timeout.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout)
					.setConnectTimeout(timeout).build();
		}

		// JDK7 新特性, 自动释放资源
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse != null) {
				Header[] headerArr = httpResponse.getHeaders("Location");
				if (headerArr == null || headerArr.length == 0) {
					logger.error("url:", url, "httpResponse code ", httpResponse.getStatusLine().getStatusCode()
							+ ",httpResponse :" + httpResponse + ",responString:" + httpResponse);
					throw new ZqException("httpPost 出错，不能获取Location");
				}
				Header header = headerArr[0];
				return header.getValue();
			} else {
				logger.error("url:", url, "httpResponse code ", httpResponse.getStatusLine().getStatusCode()
						+ ",httpResponse :" + httpResponse + ",responString:" + httpResponse);
				throw new ZqException("httpPost 出错，返回状态!=200");
			}
			// ConnectTimeoutException 包含了 ConnectionTimeout 和ConnectionPoolTimeout
		} catch (SocketTimeoutException | ConnectTimeoutException e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqHttpTimeOutException("http执行超时， e=" + e);
		} catch (Exception e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		}
	}

	public static String executeHead(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
		HttpHead httpHead = new HttpHead(wrapParam(url, paramMap));
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpHead.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		// JDK7 新特性, 自动释放资源
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpResponse httpResponse = httpClient.execute(httpHead);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return "OK";
			}
		} catch (Exception e) {
			logger.error("StockHttpClientUtil", "excuteGet", "执行出错" + e);
		}
		return "failed";
	}

	/**
	 * 默认不设置超时
	 * 
	 * @param url
	 *            请求地址
	 * @param headerMap
	 *            请求头
	 * @param paramMap
	 *            请求其他参数
	 * @return
	 */
	public static String executePost(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
		return executePost(url, headerMap, paramMap, null);
	}

	/**
	 *
	 * @param url
	 *            请求地址
	 * @param headerMap
	 *            请求头
	 * @param paramMap
	 *            请求其他参数
	 * @param timeout
	 *            超时时间毫秒值
	 * @return
	 */
	public static String executePost(String url, Map<String, String> headerMap, Map<String, String> paramMap,
			Integer timeout) {
		HttpPost httpPost = new HttpPost(url);
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		// 添加参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), StringUtils.trimToEmpty(entry.getValue())));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeout != null && timeout.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout)
					.setConnectTimeout(timeout).build();
		}

		// JDK7 新特性, 自动释放资源
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			} else {
				logger.error("url:", url, "httpResponse code ", httpResponse.getStatusLine().getStatusCode()
						+ ",httpResponse :" + httpResponse + ",responString:" + httpResponse);
				throw new ZqException("" + httpResponse.getStatusLine().getStatusCode(), "httpPost 出错，返回状态!=200");
			}
			// ConnectTimeoutException 包含了 ConnectionTimeout 和ConnectionPoolTimeout
		} catch (SocketTimeoutException | ConnectTimeoutException e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqHttpTimeOutException("http执行超时， e=" + e);
		} catch (ZqException e) {
			logger.error("ZqHttpClientUtil", "excutePost", "ZqException异常", url, ", 异常：", e);
			throw e;
		} catch (Exception e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		}
	}

	public static String executJSONPoolManagerPost(CloseableHttpClient httpClient, String url,
			Map<String, String> headerMap, JSONArray json, int socketTimeOut, int connectTimeout,
			int connectionRequestTimeout) {
		HttpPost httpPost = new HttpPost(url);

		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		// 设置超时相关内容
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
		httpPost.setConfig(requestConfig);

		try {
			// 为每个http 设置HttpContext， 保证线程安全
			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost, context);
			try {
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
				} else {
					throw new ZqException("httpPost 出错，返回状态!=200");
				}
			} finally {
				httpResponse.close();
			}

		} catch (SocketTimeoutException | ConnectTimeoutException e) {
			logger.error("ZqHttpClientUtil", "executePoolManagerPost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqHttpTimeOutException("http执行超时， e=" + e);
		} catch (Throwable e) {
			logger.error("ZqHttpClientUtil", "executePoolManagerPost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		} finally {
			httpPost.releaseConnection();
		}
	}

	/**
	 * 按json格式发送
	 *
	 * @param url
	 * @param headerMap
	 * @param json
	 *            json化后的参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String postJSON(String url, Map<String, String> headerMap, JSONArray json, Integer timeout)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeout != null && timeout.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout)
					.setConnectTimeout(timeout).build();
		}
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			} else {
				logger.error("postjson error,url:", url, "error info:", httpResponse.getStatusLine(),
						EntityUtils.toString(httpEntity, StandardCharsets.UTF_8));
				throw new ZqException("httpPost error" + httpResponse.getStatusLine());
			}
		} catch (Exception e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		}
	}

	/**
	 * 按json格式发送
	 *
	 * @param url
	 * @param headerMap
	 * @param json
	 *            json化后的参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String postJSON(String url, Map<String, String> headerMap, JSONObject json, Integer timeout)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		StringEntity stringEntity = new StringEntity(json.toString(), "utf-8");
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeout != null && timeout.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout)
					.setConnectTimeout(timeout).build();
		}
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			} else {
				logger.error("postjson error,url:", url, "error info:", httpResponse.getStatusLine(),
						EntityUtils.toString(httpEntity, StandardCharsets.UTF_8));
				throw new ZqException("httpPost error" + httpResponse.getStatusLine());
			}
		} catch (Exception e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		}
	}

	/**
	 *
	 * @param url
	 * @param headerMap
	 * @param json
	 * @param timeout
	 * @return 获得 Cookie 和 请求结果 目前 UnspayChannelImpl--prechargeToChannelByQuickV0使用
	 */
	public static Map<String, String> postJSONReturnCookieAndResult(String url, Map<String, String> headerMap,
			JSONObject json, Integer timeout) {
		Map<String, String> results = new HashMap<String, String>();
		HttpPost httpPost = new HttpPost(url);
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		StringEntity stringEntity = new StringEntity(json.toString(), "utf-8");
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeout != null && timeout.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout)
					.setConnectTimeout(timeout).build();
		}
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String response = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			results.put("response", response);
			// 获取cookie
			HeaderIterator it = httpResponse.headerIterator("Set-Cookie");
			String Cookie = null;
			String Path = null;
			while (it.hasNext()) {
				String[] setCookie = it.next().toString().split(";");
				for (String st : setCookie) {
					if (st.contains("JSESSIONID")) {
						Cookie = st.substring(st.indexOf("=") + 1);
					} else if (st.contains("Path")) {
						Path = st.substring(st.indexOf("=") + 1);
					}

				}
			}
			results.put("Cookie", "JSESSIONID=" + Cookie);
			results.put("Path", Path);
			return results;
		} catch (Exception e) {
			logger.error("ZqHttpClientUtil", "excutePost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		}
	}

	public static String executeGet(String url) {
		return executeGet(url, null, null);
	}

	public static String executeGet(String url, Map<String, String> paramMap) {
		return executeGet(url, null, paramMap);
	}

	public static String executeGet(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
		HttpGet httpGet = new HttpGet(wrapParam(url, paramMap));
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpGet.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		// JDK7 新特性, 自动释放资源
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			} else {
				throw new ZqException("httpGet 出错，返回状态!=200");
			}
		} catch (Exception e) {
			logger.error("StockHttpClientUtil", "excuteGet", "执行出错");
			throw new ZqException("执行出错， e=" + e);
		}
	}

	public static String executeGet(String url, Map<String, String> headerMap, Map<String, String> paramMap,
			Integer timeOut) {
		HttpGet httpGet = new HttpGet(wrapParam(url, paramMap));
		// 添加Header
		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpGet.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeOut != null && timeOut.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectionRequestTimeout(timeOut)
					.setConnectTimeout(timeOut).build();
		}

		// JDK7 新特性, 自动释放资源
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			} else {
				throw new ZqException("httpGet 出错，返回状态!=200");
			}
		} catch (Exception e) {
			logger.error("StockHttpClientUtil", "excuteGet", "执行出错");
			throw new ZqException("执行出错， e=" + e);
		}
	}

	public static String executeGet(String url, Integer timeOut) {
		HttpGet httpGet = new HttpGet(wrapParam(url, null));

		// 设置超时相关内容
		RequestConfig requestConfig = null;
		if (timeOut != null && timeOut.intValue() > 0L) {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectionRequestTimeout(timeOut)
					.setConnectTimeout(timeOut).build();
		}

		// JDK7 新特性, 自动释放资源
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
			} else {
				throw new ZqException("httpGet 出错，返回状态!=200");
			}
		} catch (Exception e) {
			logger.error("StockHttpClientUtil", "excuteGet", "执行出错");
			throw new ZqException("执行出错， e=" + e);
		}
	}

	// public static String executeGet(String url, Map<String, String> headerMap, Map<String, String> paramMap,
	// CloseableHttpClient httpClient) {
	// HttpGet httpGet = new HttpGet(wrapParam(url, paramMap));
	// // 添加Header
	// if (!CollectionUtils.isEmpty(headerMap)) {
	// for (Map.Entry<String, String> entry : headerMap.entrySet()) {
	// httpGet.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
	// }
	// }
	// // JDK7 新特性, 自动释放资源
	// try {
	// HttpResponse httpResponse = httpClient.execute(httpGet);
	// if (httpResponse.getStatusLine().getStatusCode() == 200) {
	// HttpEntity httpEntity = httpResponse.getEntity();
	// return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
	// } else {
	// throw new ZqException("httpGet 出错，返回状态!=200");
	// }
	// } catch (Exception e) {
	// logger.error("StockHttpClientUtil", "excuteGet", "执行出错");
	// throw new ZqException("执行出错， e=" + e);
	// }
	// }

	private static URI wrapParam(String url, Map<String, String> paramMap) {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			if (CollectionUtils.isEmpty(paramMap)) {
				return uriBuilder.build();
			}
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				uriBuilder.addParameter(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
			return uriBuilder.build();
		} catch (URISyntaxException e) {
			throw new ZqException("URI syntax error", e);
		}
	}

	// ========================================================连接池调用=================================================

	public static void main(String[] args) {

		PoolingHttpClientConnectionManager entrustConnectionManager = ZqHttpClientUtil.getHttpClientConnectionManager(
				10000, 2);
		final HashMap<String, String> ENTRUST_AUTH_HEADER = new HashMap<String, String>();
		ENTRUST_AUTH_HEADER
				.put("Cookie",
						"serverId=4; login-account=31000007; orgIdLastNumber=0; zhuanqianSessionId=CED3448626240BB6DBC7FFA1EBD77153; zquk=784D79B0602A0D09;");
		// ENTRUST_AUTH_HEADER.put("Connection", "Keep-Alive");
		final String url = "http://www.niuqia.com/systemPlatform/queryExceptionStockOrders";

		final HashMap<String, String> param = new HashMap<String, String>();
		param.put("beginDate", "2017-03-07 09:45:30");
		param.put("endDate", "2017-03-17 09:45:30");
		param.put("orderQueryRange", "2");
		param.put("beginIndex", "0");
		param.put("pageSize", "2");
		final CloseableHttpClient httpClient = getHttpClient(entrustConnectionManager);

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String response = ZqHttpClientUtil.executePoolManagerPost(httpClient, url, ENTRUST_AUTH_HEADER,
							param, 10000);
					System.out.println(response.length() + "index" + System.currentTimeMillis());

				}
			}, "thread-name" + i).start();
		}

	}

	public static PoolingHttpClientConnectionManager getHttpClientConnectionManager(int maxTotal, int defaultMaxPerRoute) {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", plainsf).register("https", sslsf).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
		connManager.setMaxTotal(maxTotal);// 总的连接池数量
		connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);// 连接到每个域名的连接数量
		return connManager;
	}

	public static CloseableHttpClient getHttpClient(PoolingHttpClientConnectionManager entrustConnectionManager) {
		return HttpClients.custom().setConnectionManager(entrustConnectionManager)
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
	}

	public static String executePoolManagerPost(CloseableHttpClient httpClient, String url,
			Map<String, String> headerMap, Map<String, String> paramMap, int timeout) {
		return executePoolManagerPost(httpClient, url, headerMap, paramMap, timeout, timeout, timeout);
		// 使用重试策略， 0， false 不重试);
	}

	public static String executePoolManagerPost(CloseableHttpClient httpClient, String url,
			Map<String, String> headerMap, Map<String, String> paramMap, int socketTimeOut, int connectTimeout,
			int connectionRequestTimeout) {
		HttpPost httpPost = new HttpPost(url);

		if (!CollectionUtils.isEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), StringUtils.trimToEmpty(entry.getValue()));
			}
		}
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		// 添加参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), StringUtils.trimToEmpty(entry.getValue())));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
		// 设置超时相关内容
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
		httpPost.setConfig(requestConfig);

		try {
			// 为每个http 设置HttpContext， 保证线程安全
			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost, context);
			try {
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
				} else {
					logger.error(httpResponse);
					throw new ZqException("httpPost 出错，返回状态!=200");
				}
			} finally {
				httpResponse.close();
			}

		} catch (SocketTimeoutException | ConnectTimeoutException e) {
			logger.error("ZqHttpClientUtil", "executePoolManagerPost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqHttpTimeOutException("http执行超时， e=" + e);
		} catch (Throwable e) {
			logger.error("ZqHttpClientUtil", "executePoolManagerPost", "执行出错, URL:", url, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		} finally {
			httpPost.releaseConnection();
		}
	}

	public static int getWithProxy(String url, Map<String, String> headers, String proxyHost, int proxyPort,
			final String userName, final String Passwd) {
		logger.info("依次代理地址，代理端口号，用户，密码：" + proxyHost + "_" + proxyPort + "_" + userName + "_" + Passwd);
		// 用户名和密码验证
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				PasswordAuthentication p = new PasswordAuthentication(userName, Passwd.toCharArray());
				return p;
			}
		});

		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", new PlainConnectionSocketFactory())
				.register("https", new ZqSSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new ZqDnsResolver());
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		try {
			InetSocketAddress socksaddr = new InetSocketAddress(proxyHost, proxyPort);
			HttpClientContext context = HttpClientContext.create();
			context.setAttribute("socks.address", socksaddr);
			HttpGet httpget = new HttpGet(url);
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpget.setHeader(key, headers.get(key));
				}
			}
			CloseableHttpResponse response = httpClient.execute(httpget, context);
			try {
				if (response.getStatusLine().getStatusCode() == 200) {
					return 1;
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception httpClientCloseException) {
				httpClientCloseException.printStackTrace();
			}
		}
		return 0;
	}

	static class ZqDnsResolver implements DnsResolver {
		@Override
		public InetAddress[] resolve(String host) throws UnknownHostException {
			// Return some fake DNS record for every request, we won't be using it
			return new InetAddress[] { InetAddress.getByAddress(new byte[] { 1, 1, 1, 1 }) };
		}
	}

	static class ZqSSLConnectionSocketFactory extends SSLConnectionSocketFactory {

		public ZqSSLConnectionSocketFactory(final SSLContext sslContext) {
			// You may need this verifier if target site's certificate is not secure
			super(sslContext, ALLOW_ALL_HOSTNAME_VERIFIER);
		}

		@Override
		public Socket createSocket(final HttpContext context) throws IOException {
			InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
			return new Socket(proxy);
		}

		@Override
		public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
				InetSocketAddress localAddress, HttpContext context) throws IOException {
			// Convert address to unresolved
			InetSocketAddress unresolvedRemote = InetSocketAddress.createUnresolved(host.getHostName(),
					remoteAddress.getPort());
			return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
		}
	}
}