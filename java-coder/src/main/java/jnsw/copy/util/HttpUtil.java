package jnsw.copy.util;


import com.alibaba.fastjson.JSONObject;
import jnsw.copy.bean.BaseRequest;
import jnsw.copy.bean.BaseResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {
    public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static RequestConfig config = null;
    private static final String DEF_CHAR_ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    private static RequestConfig getConfig() {
        if (config == null) {
            int timeout = 60000;
            logger.info("init http client connection timeout:" + timeout);
            config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        }
        return config;
    }

    private static CloseableHttpClient getHttpClient(String url) {
        return getNoSSLConnection();
    }

    private static CloseableHttpClient getNoSSLConnection() {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getConfig()).setRetryHandler(new DefaultHttpRequestRetryHandler(0,false)).build();
        return httpClient;
    }

    public static String requestPost(BaseRequest head, String request) throws Exception {
        CloseableHttpResponse response = null;
        long start = System.currentTimeMillis();
        String url = head.getUrl();
        logger.info("*************start request " + url + "**************");
        logger.info("request message:" + request);
        try {
            String timestamp = new Long(System.currentTimeMillis()).toString();
            String endata = null;
            String appsecret = "null";
            appsecret = head.getAppsecret();
            logger.info("transcode：" + head.getTranscode());
            logger.info("appsecret：" + appsecret);
            logger.info("appid：" + head.getAppid());
            logger.info("orgno：" + head.getOrgno());
            logger.info("url：" + head.getUrl());

            endata = SignUtil.encrypt(request, appsecret);
            logger.info("加密后数据：" + endata);
            String signature = SignUtil.makeSignature(head.getAppid(),
                    appsecret, timestamp, endata);
            logger.info("签名信息：" + signature);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", CONTENT_TYPE);
            httpPost.addHeader("Accept-Charset", DEF_CHAR_ENCODING);
            httpPost.addHeader("x-ihive-appid", head.getAppid());
            httpPost.addHeader("x-ihive-timestamp", timestamp);
            httpPost.addHeader("x-ihive-service", head.getTranscode());
            httpPost.addHeader("x-ihive-signature", signature);
            httpPost.addHeader("x-ihive-orgno", head.getOrgno());

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("endata", endata));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient httpClient = getHttpClient(url);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new Exception("返回状态:" + String.valueOf(statusCode));
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, DEF_CHAR_ENCODING);
            } else {
                throw new Exception("返回结果为空");
            }
            EntityUtils.consume(entity);

            logger.info("response message :" + result);
            JSONObject json = JSONObject.parseObject(result);
            BaseResponse baseResponse = json.toJavaObject(BaseResponse.class);
            if (baseResponse.getEndata() == null || "".equals(baseResponse.getEndata())) {
                return result;
            } else {
                result = SignUtil.decrypt(baseResponse.getEndata(), appsecret);
                logger.info("返回数据信息：" + result);
                return result;
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("请求[" + url + "]异常");
            if (null == response) {
                throw new Exception("****请求服务器失败****（服务未启动或链接超时）");
            } else {
                throw e;
            }
        } finally {
            logger.info("*********end request [" + url + "] [" + (System.currentTimeMillis() - start) + "*********");
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
