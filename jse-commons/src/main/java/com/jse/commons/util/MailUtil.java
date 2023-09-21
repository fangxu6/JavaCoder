package com.jse.commons.util;

import com.zhuanqian.commons.exception.ZqException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * 通过linux发送邮件
 * 
 * @company jse-zq
 * @author Infernalzero
 * @version MailUtil.java, v 0.1 2015年7月29日 上午11:13:25
 */

public class MailUtil {

	/**
	 * 发送邮件
	 *
	 * @param subject
	 * @param message
	 * @param targetAddress
	 * @throws IOException 
	 */
	public static void sendMail(String subject, String message, String targetAddress){
		String command = "echo \"" + message + "\"|mail -s \"" + subject + "\" " + targetAddress;
			Process process = BashUtil.runCommand(command);
			String executeInfo = BashUtil.getInputStream(process);
			if(StringUtils.isNotBlank(executeInfo)){
				throw new ZqException("邮件发送异常 "+executeInfo);
			}
	}
}
