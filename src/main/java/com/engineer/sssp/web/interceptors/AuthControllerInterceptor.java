package com.engineer.sssp.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.engineer.sssp.commons.AppContext;
import com.engineer.sssp.commons.constants.EngineerConstant;
import com.engineer.sssp.entity.User;
import com.engineer.sssp.service.UserService;

/**
 * 权限拦截器
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月8日 下午6:11:08
 */
public class AuthControllerInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(AuthControllerInterceptor.class);
	
	private UserService userService = AppContext.getBean("userService");
	
	/**
	 * 在Controller方法后进行拦截 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.debug("=======auth afterCompletion=========");
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("=======auth postHandle=========");
		super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 在Controller方法前进行拦截 如果返回false 从当前拦截器往回执行所有拦截器的afterCompletion方法,再退出拦截器链. 如果返回true 执行下一个拦截器,直到所有拦截器都执行完毕. 再运行被拦截的Controller.
	 * 然后进入拦截器链,从最后一个拦截器往回运行所有拦截器的postHandle方法. 接着依旧是从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("=======auth preHandle=========");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		User user = userService.findByUserNameAndPassword(userName, password);
		if(null != user) {
			request.getSession().setAttribute("user", user);
			if(user.getCode().endsWith(EngineerConstant.ADMIN_CODE)) {
				return true;
			} else {
				//查询不是admin的权限
			}
		}
		return false;
	}
}
