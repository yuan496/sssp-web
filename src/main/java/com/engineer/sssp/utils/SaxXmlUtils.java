package com.engineer.sssp.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月18日 上午9:04:58
 */
public class SaxXmlUtils extends DefaultHandler {
	
	private static Logger log = LoggerFactory.getLogger(SaxXmlUtils.class);
	/** 日期数据格式化 */
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 解析后的所有对象集合
	private List<Object> list = new ArrayList<Object>();
	
	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		log.debug("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		log.debug("----文档解释结束----");
	}
	
	/**
	 * 通过className生成该对象并通过属性表为其赋值.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午9:06:54
	 * @param className
	 * @param fieldMap
	 * @return
	 * @throws Exception
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午9:06:54
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private Object parseObject(String className, Map<String, String> fieldMap) throws Exception {
		Class clazz = Class.forName(className);
		Object object = clazz.newInstance();
		Field[] fields = ReflectUtils.getDeclaredFieldsIncludeInherit(clazz);
		for (Field field : fields) {
			if (field.getModifiers() == Modifier.STATIC) {//排除静态变量
				continue;
			}
			String fieldName = field.getName();
			if (fieldMap.containsKey(fieldName)) {
				// 获取字段的set方法
				StringBuffer methodName = new StringBuffer();
				methodName.append("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
				Class fieldClazz = field.getType();
				Class[] parameterTypes = new Class[] {fieldClazz};
				Method method = clazz.getMethod(methodName.toString(), parameterTypes);
				// 根据字段不同的类型，对方法传入相应类型的参数
				Object[] paraValues = new Object[1];
				if (StringUtils.isEmpty(fieldMap.get(fieldName))) {
					paraValues[0] = null;
				} else {
					if (fieldClazz.equals(Integer.class) || fieldClazz.equals(int.class)) {
						paraValues[0] = Integer.valueOf(fieldMap.get(fieldName));
					} else if (fieldClazz.equals(String.class)) {
						paraValues[0] = fieldMap.get(fieldName);
					} else if (fieldClazz.equals(Date.class)) {
						paraValues[0] = dateFormat.parse(fieldMap.get(fieldName));
					} else if (fieldClazz.equals(Short.class) || fieldClazz.equals(short.class)) {
						paraValues[0] = Short.valueOf(fieldMap.get(fieldName));
					} else if (fieldClazz.equals(Boolean.class) || fieldClazz.equals(boolean.class)) {
						paraValues[0] = Boolean.valueOf(fieldMap.get(fieldName));
					} else if (fieldClazz.equals(Double.class) || fieldClazz.equals(double.class)) {
						paraValues[0] = Double.valueOf(fieldMap.get(fieldName));
					} else if (Enum.class.isAssignableFrom(fieldClazz)) {
						// 枚举转换
						paraValues[0] = Enum.valueOf(fieldClazz, fieldMap.get(fieldName));
					}
				}
				// 调用方法对字段赋值
				method.invoke(object, paraValues);
			}
		}
		return object;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (null != attributes && attributes.getLength() > 0) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < attributes.getLength(); j++) {
					String name = attributes.getQName(j);
					String value = attributes.getValue(j);
					map.put(name, value);
				}
				Object object = this.parseObject(qName, map);
				if (null != object) {
					list.add(object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.debug("<");
		log.debug(qName);
		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				log.debug(" " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"");
			}
		}
		log.debug(">");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		log.debug("</");
		log.debug(qName);
		log.debug(">");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		log.debug(new String(ch, start, length));
	}
	
	/**
	 * @return the list
	 */
	public List<Object> getList() {
		return list;
	}
	
	
}
