package com.engineer.sssp.commons;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * spring mvc controller的基类
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月2日 下午3:19:25
 */
@Controller
public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 服务名称
	 */
	private String serverName = "";
	private String ctx = "";
	private HttpSession session;
	
	/**
	 * 根据spring的validate验证返回结果，转换成map信息返回给页面,页面获取方式${errors?.属性名}
	 * @author fangzhibin 2015年3月26日 下午2:40:06
	 * @param result spring的validate验证结果
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
	
	/**
	 * 获取客户端ip地址
	 * @author fangzhibin 2015年5月20日 上午11:48:12
	 * @param request
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月20日 上午11:48:12
	 */
	protected String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public HttpSession getSession() {
		return session;
	}
	
	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	public String getCtx() {
		return ctx;
	}
	
	public void setCtx(String ctx) {
		this.ctx = ctx;
	}
	
	public String redirect(String path) {
		return "redirect:" + path;
	}
}
