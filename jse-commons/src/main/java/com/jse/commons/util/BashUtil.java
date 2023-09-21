/**
 *  Copyright (c) 2015, jse-zq. All rights reserved.
 */
package com.jse.commons.util;

import com.jse.commons.exception.ZqException;
import com.jse.commons.log.ZqLogger;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 调用RunTime类执行命令
 * 
 * @company jse-zq
 * @author dango
 * @version BashUtil.java, v 0.1 2015年4月24日 下午1:18:33
 */
public class BashUtil {
	private static final ZqLogger	logger	= new ZqLogger(BashUtil.class);

	// FIXME 传入参数需转义字符暂未处理
	public static Process runCommand(String commandLine) {
		logger.info("BashUtil", "runCommand", commandLine);
		try {
			String[] commandArray = new String[] { "/bin/bash", "-c", commandLine };
			return Runtime.getRuntime().exec(commandArray);
		} catch (IOException e) {
			throw new ZqException(e.getMessage(), e);
		}
	}

	/**
	 * 获取win系统的本地Ip地址
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getLocalIpFromHostAddress() {
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "【未知Ip】";
		}
	}

	/**
	 * 获取当前主机的主机名
	 *
	 * @return
	 * @throws IOException
	 */
	public static String getHostname() throws IOException {
		String command = "hostname";
		Process process = runCommand(command);
		String hostname = getInputStream(process);
		return hostname;
	}

	/**
	 * 检测程序执行状态，若仍在执行则继续sleep
	 *
	 * @param program
	 *            运行的程序名称
	 * @param executeCommand
	 *            检查 ps的结果中是否存在该字符串
	 */
	public static void checkExecuteStatus(String program, String executeCommand) {
		int count = 0;
		String command = "ps -ef|grep " + program + "|grep -v 'grep'";
		String line;
		try {
			while (true) {
				logger.info("BashUtil", "checkExecuteStatus", command);
				Process process = runCommand(command);
				InputStreamReader ir = new InputStreamReader(process.getInputStream());
				BufferedReader input = new BufferedReader(ir);
				while ((line = input.readLine()) != null) {
					if (line.contains(executeCommand)) {
						count++;
					}
				}
				if (count == 0) {
					return;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			throw new ZqException(e.getMessage(), e);
		}
	}

	/**
	 * 若进程中包含其中一个程序，则继续等待
	 *
	 * @param programs
	 */
	public static void checkExecuteStatusWithMultiCondition(String... programs) {
		String command = "ps -ef|grep -E '";
		for (int i = 0; i < programs.length; i++) {
			if (i == programs.length - 1) {
				command += programs[i];
			} else {
				command += programs[i] + "|";
			}
		}
		command += "'|grep -v 'grep'|wc -l";
		try {
			while (true) {
				logger.info("BashUtil", "checkExecuteStatusWithMultiCondition", command);
				Process process = runCommand(command);
				InputStreamReader ir = new InputStreamReader(process.getInputStream());
				BufferedReader input = new BufferedReader(ir);
				if ("0".equals(input.readLine())) {
					return;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			throw new ZqException(e.getMessage(), e);
		}
	}

	/**
	 * 获取命令执行后的输入流
	 *
	 * @param process
	 * @return
	 * @throws IOException
	 */
	public static String getInputStream(Process process, String... checkInfo) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader ir = new InputStreamReader(process.getInputStream(),"GBK");
			BufferedReader input = new BufferedReader(ir);
			String line;
			while ((line = input.readLine()) != null) {
				if (checkInfo != null) {
					for (String check : checkInfo) {
						if (line.contains(check)) {
							throw new ZqException(line);
						}
					}
				}
				sb.append(line);
			}
			input.close();
		} catch (IOException e) {
			throw new ZqException("read inputstream error");
		}
		return sb.toString();
	}

	/**
	 * 获取执行命令的结果，根据分隔符分开
	 *
	 * @param account
	 * @param serverName
	 *            为空则在本机运行
	 * @param command
	 * @param separator
	 * @return
	 */
	public static String getCommandResultBySeparator(String account, String serverName, String command, String separator) {
		if (StringUtils.isNotBlank(serverName)) {
			command = "ssh -l " + StringUtils.trimToEmpty(account) + " " + serverName + " \"" + command + "\"";
		}
		logger.info("BashUtil", "getCommandResultBySeparator", command);
		Process process = runCommand(command);
		InputStreamReader ir = new InputStreamReader(process.getInputStream());
		BufferedReader input = new BufferedReader(ir);
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = input.readLine()) != null) {
				sb.append(line).append(separator);
			}
			input.close();
		} catch (IOException e) {
			throw new ZqException("read inputstream error");
		}
		return sb.toString();
	}

	/**
	 * 远程执行命令
	 *
	 * @param account
	 * @param serverName
	 * @param command
	 */
	public static String executeOnRemoteServer(String account, String serverName, String command) {
		command = "ssh -l " + account + " " + serverName + " \"" + command + "\"";
		System.out.println(command);
		Process process = runCommand(command);
		String executeResult = getInputStream(process);
		return executeResult;
	}

	/**
	 * 远程执行命令
	 *
	 * @param account
	 * @param serverName
	 * @param command
	 */
	public static String executeOnRemoteServerForDeleteJar(String account, String serverName, String command,
			String splitStr) {
		command = "ssh -l " + account + " " + serverName + " \"" + command + "\"";
		Process process = runCommand(command);
		String executeResult = getInputStream(splitStr, process);
		return executeResult;
	}

	/**
	 * 获取命令执行后的输入流
	 *
	 * @param process
	 * @return
	 * @throws IOException
	 */
	public static String getInputStream(String split, Process process) {
		InputStreamReader ir = new InputStreamReader(process.getInputStream());
		BufferedReader input = new BufferedReader(ir);
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = input.readLine()) != null) {
				sb.append(line).append(split);
			}
			input.close();
		} catch (IOException e) {
			throw new ZqException("read inputstream error");
		}
		return sb.toString();
	}

	/**
	 * 远程执行命令
	 *
	 * @param account
	 * @param serverName
	 * @param command
	 */
	public static String executeOnRemoteReturnToFile(String account, String serverName, String command, String file) {
		command = "ssh -l " + account + " " + serverName + " \"" + command + "\"   >  " + file;
		logger.info("BashUtil", "executeOnRemoteReturnToFile", command);
		Process process = runCommand(command);
		String executeResult = getInputStream(process);
		return executeResult;
	}
}
