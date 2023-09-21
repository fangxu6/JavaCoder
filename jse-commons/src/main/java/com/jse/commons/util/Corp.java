package com.jse.commons.util;

public enum Corp {
	NIUQIA("", ""),
	/**
	 * 乾牛报警专用应用
	 */
	QIANNIU("wwfcc0e6eca165f958", "mORJ6lmxwenrtejQBGoULcYQ2lWhMNEJfk_mihZENxc");
	/**
	 * 企业id
	 */
	String	corpId;
	/**
	 * 企业应用的密钥
	 */
	String	secret;

	private Corp(String corpId, String secret) {
		this.corpId = corpId;
		this.secret = secret;
	}

	public String getCorpId() {
		return corpId;
	}

	public String getSecret() {
		return secret;
	}

}