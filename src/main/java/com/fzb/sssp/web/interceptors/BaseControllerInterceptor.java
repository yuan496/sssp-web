package com.fzb.sssp.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fzb.sssp.commons.BaseController;

/**
 * 基础页面拦截器
 * @author fangzhibin 2015年4月1日 上午9:12:41
 * @version V1.0
 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:12:41
 */
public class BaseControllerInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(BaseControllerInterceptor.class);
	private String ctx = "";
	
	/**
	 * 在Controller方法后进行拦截 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	 * @author fangzhibin 2015-12-5 上午12:55:50
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @modify: {原因} by silencon 2015-12-5 上午12:55:50
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.debug("=======basic afterCompletion=========");
	}
	
	/**
	 * 
	 * @author fangzhibin 2015-12-5 上午12:56:11
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @modify: {原因} by silencon 2015-12-5 上午12:56:11
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("=======basic postHandle=========");
		super.postHandle(request, response, handler, modelAndView);
		request.setAttribute("ctxpath", ctx);
	}
	
	/**
	 * 在Controller方法前进行拦截 如果返回false 从当前拦截器往回执行所有拦截器的afterCompletion方法,再退出拦截器链. 如果返回true 执行下一个拦截器,直到所有拦截器都执行完毕. 再运行被拦截的Controller.
	 * 然后进入拦截器链,从最后一个拦截器往回运行所有拦截器的postHandle方法. 接着依旧是从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	 * @author fangzhibin 2015-12-5 上午12:56:25
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @modify: {原因} by silencon 2015-12-5 上午12:56:25
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("=======basic preHandle=========");
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod)handler;
			if (null != method && method.getBean() instanceof BaseController) {
				BaseController controller = (BaseController)method.getBean();
				controller.setServerName(request.getServerName());
				controller.setSession(request.getSession());
				if (StringUtils.isBlank(ctx) && StringUtils.isNotBlank(request.getContextPath()) && !StringUtils.equals(request.getContextPath(), "/")) {
					ctx = request.getContextPath();
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
}
