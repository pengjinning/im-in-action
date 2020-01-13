package io.xiaper.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 设置异常
 * @author xiaoleilu
 */
public class HttpConfigException extends RuntimeException{
	private static final long serialVersionUID = -4134588858314744501L;

	public HttpConfigException(Throwable e) {
		super(e);
	}
	
	public HttpConfigException(String message) {
		super(message);
	}
	
	public HttpConfigException(String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}
	
	public HttpConfigException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public HttpConfigException(Throwable throwable, String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
