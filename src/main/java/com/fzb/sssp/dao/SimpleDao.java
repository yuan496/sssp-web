package com.fzb.sssp.dao;

import com.fzb.sssp.commons.basic.DBType;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月18日 上午9:27:49
 */
public interface SimpleDao {
	
	public DBType getCurrentDialect();
	
	public void saveEntity(Object obj);
	
	public void executeSql(String sql);
}
