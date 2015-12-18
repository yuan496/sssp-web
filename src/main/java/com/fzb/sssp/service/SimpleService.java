package com.fzb.sssp.service;

import java.util.List;
import com.fzb.sssp.commons.basic.DBType;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月18日 上午9:25:59
 */
public interface SimpleService {
	
	public DBType getCurrentDialect();
	
	public boolean excuteSqlFile(String fileName, DBType dbType);
	
	/**
	 * 保存列表
	 * @param list
	 */
	public void saveAllIgnoreFailure(List<Object> list);
	
	/**
	 * 保存单个对象
	 * @param object
	 */
	public void save(Object object);
}
