package com.engineer.sssp.commons;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用本地线程上下文
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月2日 下午3:28:37
 */
public class ThreadLocalContext {
	
	private static Map<String, ThreadLocal<Object>> map = new ConcurrentHashMap<String, ThreadLocal<Object>>();
	
	/**
	 * 
	 * 根据key获取对应本地线程值.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:28:45
	 * @param key
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:28:45
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		ThreadLocal<Object> local = map.get(key);
		if (null != local) {
			return (T)local.get();
		}
		return null;
	}
	
	/**
	 * 
	 * 根据key设置对应本地线程值.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:29:03
	 * @param key
	 * @param obj
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:29:03
	 */
	public static void set(String key, Object obj) {
		ThreadLocal<Object> local = map.get(key);
		synchronized (map) {
			if (null == local) {
				local = new ThreadLocal<Object>();
				map.put(key, local);
			}
		}
		if (null != local) {
			local.set(obj);
		}
	}
	
	/**
	 * 
	 * 删除本地线程值.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月2日 下午3:29:15
	 * @param key
	 * @modify {上次修改原因} by fangzhibin 2015年12月2日 下午3:29:15
	 */
	public static void remove(String key) {
		ThreadLocal<Object> local = map.get(key);
		if (null != local) {
			local.remove();
		}
	}
}
