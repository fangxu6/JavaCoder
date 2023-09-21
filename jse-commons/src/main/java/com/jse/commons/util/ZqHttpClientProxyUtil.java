package com.jse.commons.util;

import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @company jse-zq
 * @author tiebin
 * @version ZqHttpClientProxyUtil.java, v 0.1 2017年8月4日 下午5:01:51
 */
public final class ZqHttpClientProxyUtil {

	private static final ZqLogger	logger	= new ZqLogger(ZqHttpClientProxyUtil.class);

	private ZqHttpClientProxyUtil() {
	}

	public static PoolingHttpClientConnectionManager initPoolingConnManagerWithoutAuthentication(int maxTotal, int defaultMaxPerRoute) {
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
		// http 代理设置
				.register("http", new ZQConnectionSocketFactory())
				// https 代理设置
				.register("https", new ZQSSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
		// 将最大连接数增加到200
		cm.setMaxTotal(maxTotal);
		// 将每个路由基础的连接增加到20
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		return cm;

		// CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static PoolingHttpClientConnectionManager initPoolingConnManager(final String proxyName,
			final String proxyPwd, int maxTotal, int defaultMaxPerRoute) {
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				PasswordAuthentication authenticator = new PasswordAuthentication(proxyName, proxyPwd.toCharArray());
				return authenticator;
			}
		});
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
		// http 代理设置
				.register("http", new ZQConnectionSocketFactory())
				// https 代理设置
				.register("https", new ZQSSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
		// 将最大连接数增加到200
		cm.setMaxTotal(maxTotal);
		// 将每个路由基础的连接增加到20
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);

		return cm;
	}

	public static HttpClientContext initHttpClientContext(String proxyHost, int proxyPort) {
		InetSocketAddress socksaddr = new InetSocketAddress(proxyHost, proxyPort);
		HttpClientContext context = HttpClientContext.create();
		context.setAttribute("socks.address", socksaddr);
		return context;
	}

	public static String executeProxySocksPost(CloseableHttpClient httpclient, HttpClientContext context, String url,
			Map<String, String> headers, Map<String, String> paramMap, String charset, int timeount) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpPost.setHeader(key, headers.get(key));
				}
			}
			if (paramMap != null) {
				// 添加参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					params.add(new BasicNameValuePair(entry.getKey(), StringUtils.trimToEmpty(entry.getValue())));
				}

				httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
			}

			// 设置超时相关内容
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeount)
					.setSocketTimeout(timeount).setConnectTimeout(timeount).build();
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpPost, context);
			try {
				return EntityUtils.toString(response.getEntity(), charset);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("socks代理httpclient post失败", e.getMessage());
		} finally {
			httpPost.releaseConnection();
		}
		return null;
	}

	public static int getAnswerWithProxySocksGet(CloseableHttpClient httpclient, HttpClientContext context, String url,
			Map<String, String> headers, Map<String, String> paramMap, int timeount) {
		HttpGet httpPost = new HttpGet(wrapParam(url, paramMap));
		try {
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpPost.setHeader(key, headers.get(key));
				}
			}

			// 设置超时相关内容
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeount)
					.setSocketTimeout(timeount).setConnectTimeout(timeount).build();
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpPost, context);
			try {
				return response.getStatusLine().getStatusCode();
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error(e, "socks代理httpclient post失败");
		} finally {
			httpPost.releaseConnection();
		}
		return -100;
	}

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

	public static void main(String[] args) {
		// 依次是代理地址，代理端口号，用户密码
		String proxyHost = "47.104.159.113";
		int proxyPort = 40006;

		String proxyName = "31050000";
		String proxyPwd = "31050000";

		// 一个连接池，一个代理用户， 代理服务器授权，可以登录服务的任何用户
		PoolingHttpClientConnectionManager connManager = ZqHttpClientProxyUtil.initPoolingConnManager(proxyName,
				proxyPwd, 200, 20);
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();
		HttpClientContext context = ZqHttpClientProxyUtil.initHttpClientContext(proxyHost, proxyPort);// socks 代理服务器
		System.out.println(getAnswerWithProxySocksGet(httpclient, context, "http://httpbin.org/ip", null, null, 10000));
	}

	/**
	 * http 代理实现类
	 */
	private static class ZQConnectionSocketFactory extends PlainConnectionSocketFactory {
		@Override
		public Socket createSocket(final HttpContext context) throws IOException {
			InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
			return new Socket(proxy);
		}

		@Override
		public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
				InetSocketAddress localAddress, HttpContext context) throws IOException {
			InetSocketAddress unresolvedRemote = InetSocketAddress.createUnresolved(host.getHostName(),
					remoteAddress.getPort());
			return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
		}
	}

	/**
	 * https 代理实现类
	 */
	private static class ZQSSLConnectionSocketFactory extends SSLConnectionSocketFactory {

		public ZQSSLConnectionSocketFactory(final SSLContext sslContext) {
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

	public static String executeProxySocksGet(CloseableHttpClient httpclient, HttpClientContext context,
			String url, Map<String, String> headers, Map<String, String> paramMap, int timeOut) {
		HttpGet httpGet = new HttpGet(wrapParam(url, paramMap));
		try {
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpGet.setHeader(key, headers.get(key));
				}
			}
			// 设置超时相关内容
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut)
					.setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet, context);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity);
			}
		} catch (Exception e) {
			logger.error(e, "socks代理httpclient post失败");
		} finally {
			httpGet.releaseConnection();
		}
		return null;
	}

}
