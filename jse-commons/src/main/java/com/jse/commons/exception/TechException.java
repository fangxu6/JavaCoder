package com.jse.commons.exception;

/**
 * 系统中的技术异常，比如交易份额锁定时指令划转由于数据库查询中断导致失败，这种异常是不需要告知客户，因此需要和ZqException区分开
 * 
 * @company jse-zq
 * @author qinbing
 * @version TechException.java, v 0.1 2019年10月12日 下午2:56:00
 */

@SuppressWarnings("serial")
public class TechException extends ZqException {

	public TechException(String message) {
		super(message);
	}

	@Override
	public String getErrorMsg() {
		return super.getErrorMsg();
	}

}
