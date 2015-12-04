package com.fzb.sssp.web.log.listener;

import java.io.FileNotFoundException;
import javax.servlet.ServletContext;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

/**
 * Convenience class that performs custom logback initialization for web environments,
 * allowing for log file paths within the web application, with the option to
 * perform automatic refresh checks (for runtime changes in logging configuration).
 * 
 * <p>Supports three init parameters at the servlet context level (that is,
 * context-param entries in web.xml):
 * 
 * <ul>
 * <li><i>"logbackConfigLocation":</i><br>
 * Location of the logback config file; either a "classpath:" location (e.g.
 * "classpath:logback.xml"), an absolute file URL (e.g. "file:C:/logback.xml),
 * or a plain path relative to the web application root directory (e.g.
 * "/WEB-INF/logback.xml"). If not specified, default log4j initialization
 * will apply ("logback.xml" or "log4j.groovy" in the class path; see the
 * logback documentation for details).
 * <li><i>"logbackRefreshInterval":</i><br>
 * Interval between config file refresh checks, in milliseconds. If not specified,
 * no refresh checks will happen, which avoids starting logback watchdog thread.
 * <li><i>"logbackExposeWebAppRoot":</i><br>
 * Whether the web app root system property should be exposed, allowing for log
 * file paths relative to the web application root directory. Default is "true";
 * specify "false" to suppress expose of the web app root system property. See
 * below for details on how to use this system property in log file locations.
 * </ul>
 *
 * <p>Note: {@code initLogging} should be called before any other Spring activity
 * (when using log4j), for proper initialization before any Spring logging attempts.
 *
 * <p>Logback's watchdog thread will asynchronously check whether the timestamp
 * of the config file has changed, using the given interval between checks.
 * A refresh interval of 1000 milliseconds (one second), which allows to
 * do on-demand log level changes with immediate effect, is not unfeasible.
 * @author fangzhibin
 * @since 2.3.0
 * @createDate 2015年10月20日 下午2:58:54
 * @see org.springframework.util.Log4jConfigurer
 * @see LogbackConfigListener
 */
public abstract class LogbackWebConfigurer {
	
	/** Parameter specifying the location of the logback config file */
	public static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";
	/**
	 * Parameter specifying the refresh interval for checking the logback config
	 * file
	 */
	public static final String REFRESH_INTERVAL_PARAM = "logbackRefreshInterval";
	/** Parameter specifying whether to expose the web app root system property */
	public static final String EXPOSE_WEB_APP_ROOT_PARAM = "logbackExposeWebAppRoot";
	
	/**
	 * Initialize logback, including setting the web app root system property.
	 * @param servletContext
	 *        the current ServletContext
	 * @see WebUtils#setWebAppRootSystemProperty
	 */
	public static void initLogging(ServletContext servletContext) {
		// Expose the web app root system property.
		if (exposeWebAppRoot(servletContext)) {
			WebUtils.setWebAppRootSystemProperty(servletContext);
		}
		// Only perform custom logback initialization in case of a config file.
		String location = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
		if (location != null) {
			// Perform actual logback initialization; else rely on logback's
			// default initialization.
			try {
				// Return a URL (e.g. "classpath:" or "file:") as-is;
				// consider a plain file path as relative to the web application
				// root directory.
				if (!ResourceUtils.isUrl(location)) {
					// Resolve system property placeholders before resolving
					// real path.
					location = SystemPropertyUtils.resolvePlaceholders(location);
					location = WebUtils.getRealPath(servletContext, location);
				}
				// Write log message to server log.
				servletContext.log("Initializing logback from [" + location + "]");
				// Initialize without refresh check, i.e. without logback's
				// watchdog thread.
				LogbackConfigurer.initLogging(location);
			} catch (FileNotFoundException ex) {
				throw new IllegalArgumentException("Invalid 'logbackConfigLocation' parameter: " + ex.getMessage());
			}
		}
	}
	
	/**
	 * Shut down logback, properly releasing all file locks and resetting the
	 * web app root system property.
	 * @param servletContext
	 *        the current ServletContext
	 * @see WebUtils#removeWebAppRootSystemProperty
	 */
	public static void shutdownLogging(ServletContext servletContext) {
		servletContext.log("Shutting down logback");
		try {
			LogbackConfigurer.shutdownLogging();
		} finally {
			// Remove the web app root system property.
			if (exposeWebAppRoot(servletContext)) {
				WebUtils.removeWebAppRootSystemProperty(servletContext);
			}
		}
	}
	
	/**
	 * Return whether to expose the web app root system property, checking the
	 * corresponding ServletContext init parameter.
	 * @see #EXPOSE_WEB_APP_ROOT_PARAM
	 */
	private static boolean exposeWebAppRoot(ServletContext servletContext) {
		String exposeWebAppRootParam = servletContext.getInitParameter(EXPOSE_WEB_APP_ROOT_PARAM);
		return (exposeWebAppRootParam == null || Boolean.valueOf(exposeWebAppRootParam));
	}
}
