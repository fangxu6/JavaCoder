package com.jse.commons.util;

import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RequestUtil {

	private static final ZqLogger	logger	= new ZqLogger(RequestUtil.class);

	public static String getParamsFromRequest(HttpServletRequest request) {
		StringBuffer paramsBuffer = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				paramsBuffer.append(line);
			}
		} catch (Throwable e) {
			logger.error("RequestUtil", "excutePost", "执行出错, URL:", e, ", 异常：", e);
			throw new ZqException("执行出错， e=" + e);
		}
		return paramsBuffer.toString();
	}

}
