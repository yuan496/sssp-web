package com.engineer.sssp.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * 使用fastjson进行json与对象之间的相互转换
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年11月30日 上午10:06:08
 */
public class FastJsonTool {
	
	/**
	 * 
	 * 对象转换成json数据.
	 * <p>
	 * 对象转换成json数据
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:06:58
	 * @param obj
	 * @param params
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:06:58
	 */
	public static String object2Json(Object obj, String... params) {
		SimplePropertyPreFilter filter = null;
		if (params != null && params.length > 0) {
			filter = new SimplePropertyPreFilter(obj.getClass(), params);
		}
		return JSONObject.toJSONString(obj, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 
	 * json数据转对象.
	 * <p>
	 * json数据转对象
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年11月30日 上午10:07:47
	 * @param json
	 * @param clazz
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年11月30日 上午10:07:47
	 */
	public static <T> T json2Object(String json, Class<T> clazz) {
		return JSONObject.parseObject(json, clazz);
	}
}
