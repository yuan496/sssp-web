package com.fzb.sssp.commons;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年11月30日 上午9:53:34
 */
public class BaseController {
	
	/**
	 * 服务名称
	 */
	private HttpSession httpSession;
	private static final String NOT_FOUND = "404";
	private static final String INTERNAL_SERVER_ERROR = "500";
	
	@ExceptionHandler({Exception.class})
	public String exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
		if (e.getClass() == NoSuchRequestHandlingMethodException.class) {
			return NOT_FOUND;
		} else {
			return INTERNAL_SERVER_ERROR;
		}
	}
	
	/**
	 * 根据spring的validate验证返回结果，转换成map信息返回给页面,页面获取方式${errors?.属性名}
	 * @author fangzhibin 2015年3月26日 下午2:40:06
	 * @param result
	 *        spring的validate验证结果
	 * @param model
	 * @modify: {原因} by fangzhibin 2014年8月26日 下午2:40:06
	 */
	protected void validate(BindingResult result, ModelMap model) {
		if (result == null || model == null) {
			return;
		}
		List<FieldError> fieldErrors = result.getFieldErrors();
		if (CollectionUtils.isNotEmpty(fieldErrors)) {
			Map<String, String> errors = new LinkedHashMap<String, String>();
			for (FieldError fe : fieldErrors) {
				errors.put(fe.getField(), fe.getDefaultMessage());
			}
			model.put("errors", errors);
		}
	}
	
	public HttpSession getHttpSession() {
		return httpSession;
	}
	
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
}
