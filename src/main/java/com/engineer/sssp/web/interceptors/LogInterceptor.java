package com.engineer.sssp.web.interceptors;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.engineer.sssp.commons.AppContext;
import com.engineer.sssp.entity.Log;
import com.engineer.sssp.entity.User;
import com.engineer.sssp.service.LogService;
import com.engineer.sssp.utils.DateUtils;
import com.engineer.sssp.utils.RequestUtils;

/**
 * 日志拦截器
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2016年1月20日 下午9:40:41
 */
public class LogInterceptor implements HandlerInterceptor {
	
	private static LogService logService = AppContext.getBean("logService");
	
	private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);
	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）
		log.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), request.getRequestURI());
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			log.info("ViewName: " + modelAndView.getViewName());
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 保存日志
		saveLog(request, handler, ex, null);
		// 打印JVM信息。
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long endTime = System.currentTimeMillis(); // 2、结束时间
		log.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m", new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
		        request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
		        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
	}
	
	private void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
		User user = (User)request.getSession().getAttribute("user");
		if (user != null && user.getId() != null) {
			Log log = new Log();
			log.setTitle(title);
			log.setType(ex == null?Log.TYPE_ACCESS:Log.TYPE_EXCEPTION);
			log.setRemoteAddr(RequestUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
		}
	}
	
	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread {
		
		private Log log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Log log, Object handler, Exception ex) {
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())) {
				if (handler instanceof HandlerMethod) {
					Method m = ((HandlerMethod)handler).getMethod();
					String title = m.getName();
					log.setTitle(title);
				}
				// 如果有异常，设置异常信息
				log.setException(ex.getMessage());
				// 如果无标题并无异常日志，则不保存信息
				if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())) {
					return;
				}
				// 保存日志信息
				log.setCreateTime(new Date());
				logService.save(log);
			}
		}
	}
}
