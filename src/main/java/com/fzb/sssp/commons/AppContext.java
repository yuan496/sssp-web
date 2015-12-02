package com.fzb.sssp.commons;

import java.util.Locale;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * 以静态变量保存Spring ApplicationContext
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月2日 下午3:23:46
 */
public class AppContext implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	/**
	 * 
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:24:28
	 * @param context
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:24:28
	 */
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:24:40
	 * @param arg0
	 * @throws BeansException
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:24:40
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if (null == AppContext.context) {
			AppContext.context = context;
		}
	}
	
	/**
	 * 
	 * 取得存储在静态变量中的ApplicationContext..
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:24:20
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:24:20
	 */
	public static ApplicationContext getContext() {
		if (null == context) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义AppContext");
		}
		return context;
	}
	
	/**
	 * 
	 * 获取spring的环境配置信息.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:24:01
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:24:01
	 */
	public static Environment getEnvironment() {
		return context.getEnvironment();
	}
	
	/**
	 * 
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:24:10
	 * @param name
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:24:10
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		T bean = null;
		try {
			bean = (T)getContext().getBean(name);
		} catch (BeansException e) {
		}
		return bean;
	}
	
	/**
	 * 
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:25:09
	 * @param clazz
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:25:09
	 */
	public static <T> T getBean(Class<T> clazz) {
		T bean = null;
		try {
			bean = getContext().getBean(clazz);
		} catch (BeansException e) {
		}
		return bean;
	}
	
	/**
	 * 
	 * 从资源文件获取默认语言的资源信息.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:27:13
	 * @param key 资源KEY
	 * @param 资源值(当前系统默认语言类型)
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:27:13
	 */
	public static String getMessage(String key) {
		return getContext().getMessage(key, null, null);
	}
	
	/**
	 * 
	 * 从资源文件获取指定语言的资源信息.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:27:13
	 * @param key 资源KEY
	 * @param 资源值(当前系统默认语言类型)
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:27:13
	 */
	public static String getMessage(String key, Locale locale) {
		return getContext().getMessage(key, null, locale);
	}
	
	/**
	 * 
	 * 从资源文件获取指定语言的资源信息.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:27:13
	 * @param key 资源KEY
	 * @param 资源值(当前系统默认语言类型)
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:27:13
	 */
	public static String getMessage(String key, Object[] objs, Locale locale) {
		return getContext().getMessage(key, objs, locale);
	}
	
}
