package com.fzb.sssp.commons.basic;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据排序对象类
 * @author fangzhibin 2015年4月1日 上午9:13:28
 * @version V1.0
 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:13:28
 */
public class DBSort {
	
	private LinkedHashMap<String, Boolean> sortMap = new LinkedHashMap<String, Boolean>();
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 属性/列名，可以多次累加，order顺序按照add顺序
	 * @author fangzhibin 2015年5月9日 下午9:11:50
	 * @param param 属性名(如果是sql语法则是列名)
	 * @param isAsc 是否升序
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午9:11:50
	 */
	public void addParam(String param, boolean isAsc) {
		if (StringUtils.isNotBlank(param)) {
			sortMap.put(param, isAsc);
		}
	}
	
	/**
	 * 组装order by 子句
	 * @author fangzhibin 2015年5月9日 下午9:16:14
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午9:16:14
	 */
	public String sort() {
		if (sortMap.isEmpty()) {
			return "";
		}
		StringBuilder sort = new StringBuilder(" ORDER BY ");
		for (Entry<String, Boolean> entry : sortMap.entrySet()) {
			if (StringUtils.isNotBlank(getAlias())) {
				sort.append(getAlias());
				sort.append(".");
			}
			sort.append(entry.getKey());
			if (null == entry.getValue() || entry.getValue().booleanValue()) {
				sort.append(" ASC");
			} else {
				sort.append(" DESC");
			}
			sort.append(",");
		}
		return sort.substring(0, sort.length() - 1);
	}
	
	private String getAlias() {
		return alias;
	}
	
	/**
	 * 设置别名，null默认等于“t”,空字符串表示不需要别名
	 * @author fangzhibin 2015年5月10日 下午12:06:36
	 * @param alias null是默认等于“t”
	 * @modify: {原因} by fangzhibin 2015年5月10日 下午12:06:36
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
