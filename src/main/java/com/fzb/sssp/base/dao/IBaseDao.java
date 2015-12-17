package com.fzb.sssp.base.dao;

/**
 * @author fangzhibin 2015-12-17 下午11:13:00
 * @version V1.0
 * @modify: {原因} by fangzhibin 2015-12-17 下午11:13:00
 */
public interface IBaseDao {
	
	/**
	 * 获取记录总数
	 * @param entityClass 实体类
	 * @return
	 */
	public <T> long getCount(Class<T> entityClass);
	
	/**
	 * 清除一级缓存的数据
	 */
	public void clear();
	
	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public void save(Object entity);
	
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public void update(Object entity);
	
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityid 实体id
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	
	/**
	 * 获取实体
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
}
