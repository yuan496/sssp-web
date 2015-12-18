package com.fzb.sssp.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射转换对象什么的泛型参数类型
 * @author fangzhibin 2015年3月31日 下午5:14:39
 * @version V1.0
 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:14:39
 */
public class GenericsTool {
	
	private static final Logger log = LoggerFactory.getLogger(GenericsTool.class);
	
	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型
	 * @author fangzhibin 2015年3月31日 下午5:15:38
	 * @param clazz 类对象
	 * @return 泛型类型
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:15:38
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型
	 * @author fangzhibin 2015年3月31日 下午5:16:11
	 * @param clazz 类对象
	 * @param index 泛型位置，从0开始
	 * @return 泛型类型
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:16:11
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class<?>)) {
			log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class<?>)params[index];
	}
}
