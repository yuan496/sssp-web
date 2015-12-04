package com.fzb.sssp.web.log.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Bootstrap listener for custom logback initialization in a web environment.
 * Delegates to {@link LogbackConfigurer} (see its javadoc for configuration details).
 *
 * <p>This listener should be registered before ContextLoaderListener in {@code web.xml}
 * when using custom logback initialization.
 * @author fangzhibin
 * @since 2.3.0
 * @createDate 2015年10月20日 下午2:30:48
 * @see LogbackConfigurer
 * @see org.springframework.web.context.ContextLoaderListener
 */
public class LogbackConfigListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		 LogbackWebConfigurer.initLogging(sce.getServletContext());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LogbackWebConfigurer.shutdownLogging(sce.getServletContext());
	}
}
