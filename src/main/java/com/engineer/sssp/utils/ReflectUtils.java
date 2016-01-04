/**
 * 
 */
package com.engineer.sssp.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * java bean 反射工具类
 * @author zhuyuyin
 * @since 1.0
 */
public class ReflectUtils {
	
	public static Field[] getXmlElementFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		XmlRootElement xmlRootElement = clazz.getClass().getAnnotation(XmlRootElement.class);
		if (null != xmlRootElement) {
		}
		return fields.toArray(new Field[] {});
	}
	
	/**
	 * 获取包含父类的所有字段.
	 * <p>
	 * 获取包含父类的所有字段
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午9:06:22
	 * @param clazz
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午9:06:22
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getDeclaredFieldsIncludeInherit(Class clazz) {
		if (null == clazz) {
			return new Field[0];
		}
		Class c = clazz;
		List<Field> fds = new ArrayList<Field>();
		while (null != c) {
			Field[] fields = c.getDeclaredFields();
			if (null != fields) {
				fds.addAll(Arrays.asList(fields));
			}
			c = c.getSuperclass();
		}
		return fds.toArray(new Field[fds.size()]);
	}
	
	/**
	 * 判断是否是集合类
	 * @author zhuyuyin 2016年1月4日 下午10:27:36
	 * @param clazz
	 * @return true/false
	 * @modify {上次修改原因} by zhuyuyin 2016年1月4日 下午10:27:36
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isCollection(Class clazz) {
		boolean flag = false;
		if (clazz.getName().equals(Collection.class.getName())) {
			flag = true;
		} else {
			Class[] interfaces = clazz.getInterfaces();
			if (null != interfaces && interfaces.length > 0) {
				for (Class inf : interfaces) {
					flag = isCollection(inf);
					if (flag) {
						break;
					}
				}
				if (!flag) {
					Class supClass = clazz.getSuperclass();
					while (!flag && null != supClass) {
						flag = isCollection(supClass);
						supClass = supClass.getSuperclass();
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否继承了指定类，包括父类
	 * @author zhuyuyin 2016年1月4日 下午10:34:18
	 * @param clazz
	 * @param superClass
	 * @return
	 * @modify {上次修改原因} by zhuyuyin 2016年1月4日 下午10:34:18
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isExtends(Class clazz, Class superClass) {
		boolean flag = false;
		if (clazz.getName().equals(superClass.getName())) {
			flag = true;
		} else {
			Class supClass = clazz.getSuperclass();
			while (!flag && null != supClass) {
				flag = isExtends(supClass, superClass);
				supClass = supClass.getSuperclass();
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否实现了指定接口,包括父类
	 * @author zhuyuyin 2016年1月4日 下午10:34:55
	 * @param clazz
	 * @param inf
	 * @return
	 * @modify {上次修改原因} by zhuyuyin 2016年1月4日 下午10:34:55
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isImplement(Class clazz, Class inf) {
		boolean flag = false;
		if (clazz.getName().equals(inf.getName())) {
			flag = true;
		} else {
			Class[] interfaces = clazz.getInterfaces();
			if (null != interfaces && interfaces.length > 0) {
				for (Class inf1 : interfaces) {
					flag = isImplement(inf1, inf);
					if (flag) {
						break;
					}
				}
			}
			if (!flag) {
				Class supClass = clazz.getSuperclass();
				while (!flag && null != supClass) {
					flag = isImplement(supClass, inf);
					supClass = supClass.getSuperclass();
				}
			}
		}
		return flag;
	}
}
