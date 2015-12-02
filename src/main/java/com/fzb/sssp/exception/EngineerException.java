package com.fzb.sssp.exception;

import com.fzb.sssp.commons.AppContext;

/**
 * 自封装的公共异常
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月2日 下午3:41:11
 */
public class EngineerException extends RuntimeException {
	
	/**
	 * TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1252847536776300536L;
	/**
	 * 错误码
	 */
	private String code;
	/**
	 * 异常信息
	 */
	private String msg;
	
	public EngineerException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public EngineerException(String code, String msg, Throwable e) {
		super(e);
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	/**
	 * 返回异常信息，若msg为空，则根据code从资源文件获取，若资源文件没有对应信息，则直接返回堆栈异常信息
	 * @author shanguoming 2015年4月1日 上午9:09:43
	 * @return
	 * @modify: {原因} by shanguoming 2015年4月1日 上午9:09:43
	 */
	@Override
	public String getMessage() {
		if (null == this.msg || "".equals(this.msg.trim())) {
			this.msg = AppContext.getMessage(code);
			if (null == this.msg || "".equals(this.msg.trim())) {
				this.msg = super.getMessage();
			}
		}
		return this.msg;
	}
}
