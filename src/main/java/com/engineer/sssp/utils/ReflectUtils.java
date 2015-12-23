/**
 * 
 */
package com.engineer.sssp.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * java bean 反射工具类
 * @author zhuyuyin
 * @since 1.0
 */
public class ReflectUtils {
	
	public static Field[] getXmlElementFields(Class<?> clazz){
		List<Field> fields = new ArrayList<Field>();
		XmlRootElement xmlRootElement = clazz.getClass().getAnnotation(XmlRootElement.class);
		if(null != xmlRootElement){
			
		}
		return fields.toArray(new Field[]{});
	}
	
}
