package com.fzb.sssp.commons;

import java.util.Locale;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年11月30日 上午9:35:37
 */
public class AppContext implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	/**
	 * 取得存储在静态变量中的ApplicationContext..
	 * <p>
	 * 取得存储在静态变量中的ApplicationContext.
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:04:13
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:04:13
	 */
	public static ApplicationContext getContext() {
		if (context == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义AppContext");
		}
		return context;
	}
	
	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 * <p>
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午9:36:13
	 * @param arg0
	 * @throws BeansException
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午9:36:13
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		AppContext.context = context;
	}
	
	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * <p>
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:04:31
	 * @param name
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:04:31
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		try {
			return (T)getContext().getBean(name);
		} catch (BeansException e) {
		}
		return (T)null;
	}
	
	/**
	 * 从资源文件获取系统默认语言的资源信息.
	 * <p>
	 * 从资源文件获取系统默认语言的资源信息
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:04:44
	 * @param key
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:04:44
	 */
	public static String getMessage(String key) {
		return getContext().getMessage(key, null, null);
	}
	
	/**
	 * 从资源文件获取指定语言的资源信息.
	 * <p>
	 * 从资源文件获取指定语言的资源信息
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:04:57
	 * @param key
	 * @param locale
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:04:57
	 */
	public static String getMessage(String key, Locale locale) {
		return getContext().getMessage(key, null, locale);
	}
	
	/**
	 * 从资源文件获取指定语言的资源信息.
	 * <p>
	 * 从资源文件获取指定语言的资源信息
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:05:10
	 * @param key
	 * @param objs
	 * @param locale
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:05:10
	 */
	public static String getMessage(String key, Object[] objs, Locale locale) {
		return getContext().getMessage(key, objs, locale);
	}
}
