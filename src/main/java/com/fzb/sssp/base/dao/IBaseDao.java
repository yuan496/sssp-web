package com.fzb.sssp.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.fzb.sssp.commons.basic.DBSort;
import com.fzb.sssp.commons.basic.DBType;

/**
 * 数据库基础接口
 * @author fangzhibin 2015年3月31日 下午5:04:31
 * @version V1.0
 * @param <T> 实体类型
 * @param <ID> 主键类型
 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:04:31
 */
public interface IBaseDao<T, ID extends Serializable> {
	
	/**
	 * 获取连接的数据库类型
	 * @author fangzhibin 2015年3月31日 下午5:08:06
	 * @return {@link DBType}
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:08:06
	 */
	DBType getDialect();
	
	/**
	 * 保存实体数据
	 * @author fangzhibin 2015年3月31日 下午5:08:54
	 * @param entity 实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:08:54
	 */
	void save(T entity);
	
	/**
	 * 批量保存实体数据
	 * @author fangzhibin 2015年3月31日 下午5:08:54
	 * @param entities 实体对象集合
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:08:54
	 */
	void saveAll(List<T> entities);
	
	/**
	 * 可以更新持久态或者脱管的实体对象，参数中的实体状态保持不变
	 * @author fangzhibin 2015年3月31日 下午6:24:59
	 * @param entity 实体对象
	 * @return 返回持久态的实体
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:24:59
	 */
	T merge(T entity);
	
	/**
	 * 批量更新持久态或者脱管的实体对象
	 * @author fangzhibin 2015年9月21日 上午10:33:05
	 * @param entities	实体对象集合
	 * @modify: {原因} by fangzhibin 2015年9月21日 上午10:33:05
	 */
	void saveOrUpdateAll(List<T> entities);
	
	/**
	 * 更新实体对象，实体必须是持久态，脱管的实体会报异常
	 * @author fangzhibin 2015年3月31日 下午6:24:15
	 * @param entity 实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:24:15
	 */
	int update(T entity);
	
	/**
	 * 批量更新实体集合，实体必须是持久态，脱管的实体会报异常
	 * @author fangzhibin 2015年9月21日 上午10:34:00
	 * @param entities	实体对象集合
	 * @modify: {原因} by fangzhibin 2015年9月21日 上午10:34:00
	 */
	void updateAll(List<T> entities);
	
	/**
	 * 根据主键，按需更新数据
	 * @author fangzhibin 2015年5月10日 下午3:51:31
	 * @param params 更新参数,其中必须包括主键名和主键值，否则异常
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月10日 下午3:51:31
	 */
	int update(Map<String, Object> params);
	
	/**
	 * 根据主键查询实体(不延迟加载)
	 * @author fangzhibin 2015年3月31日 下午5:27:58
	 * @param id 主键值
	 * @return 实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:27:58
	 */
	T get(ID id);
	
	/**
	 * 根据主键集合批量查询对象
	 * @author fangzhibin 2015年10月15日 下午4:54:55
	 * @param ids
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月15日 下午4:54:55
	 */
	List<T> getByIds(List<ID> ids);
	
	/**
	 * 根据参数查询唯一实体，支持集合对象，如果没有查询到实体会抛出异常
	 * @author fangzhibin 2015年10月15日 下午2:11:54
	 * @param param
	 * @param val
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月15日 下午2:11:54
	 */
	T getUnique(String param, Object val);
	
	/**
	 * 根据参数查询唯一实体，支持集合对象，如果没有查询到实体会抛出异常
	 * @author fangzhibin 2015年10月15日 上午11:00:55
	 * @param params
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月15日 上午11:00:55
	 */
	T getUnique(Map<String, Object> params);
	
	/**
	 * 查询全部信息
	 * @author fangzhibin 2015年5月9日 下午8:43:52
	 * @param sort 排序对象,null则表示默认排序
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午8:43:52
	 */
	List<T> getAll(DBSort sort);
	
	/**
	 * 获取表的总行数
	 * @author fangzhibin 2015年10月15日 下午6:08:51
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月15日 下午6:08:51
	 */
	long getRowCount();
	
	/**
	 * 根据属性查询数据,支持集合对象
	 * @author fangzhibin 2015年5月9日 下午8:49:40
	 * @param param 属性名
	 * @param val 属性值
	 * @param sort 排序对象,null则表示默认排序
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午8:49:40
	 */
	List<T> findByParam(String param, Object val, DBSort sort);
	
	/**
	 * 根据属性map查询数据,支持集合对象
	 * @author fangzhibin 2015年5月9日 下午8:50:52
	 * @param params 属性的map
	 * @param sort 排序对象,null则表示默认排序
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午8:50:52
	 */
	List<T> findByParams(Map<String, Object> params, DBSort sort);

	/**
	 * 根据实体对象删除数据,注意对象必须是非游离态,否则异常
	 * @author fangzhibin 2015年3月31日 下午5:57:52
	 * @param entity 实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:57:52
	 */
	void delete(T entity);
	
	/**
	 * 根据实体对象集合批量删除数据,注意对象必须是非游离态,否则异常
	 * @author fangzhibin 2015年3月31日 下午5:58:03
	 * @param entities 实体对象集合
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:58:03
	 */
	void deletes(Collection<T> entities);
	
	/**
	 * 根据主键删除数据
	 * @author fangzhibin 2015年3月31日 下午6:02:30
	 * @param id 主键值
	 * @return true:删除成功,false:删除失败(数据已经不存或者异常)
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:02:30
	 */
	boolean deleteById(ID id);
	
	/**
	 * 根据主键集合批量删除数据
	 * @author fangzhibin 2015年3月31日 下午6:03:41
	 * @param ids 主键值集合
	 * @return 实际删除数据条数
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:03:41
	 */
	int deleteByIds(List<ID> ids);
	
	/**
	 * 根据参数删除表内数据，支持集合对象
	 * @author fangzhibin 2015年10月15日 下午2:06:20
	 * @param param
	 * @param val
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月15日 下午2:06:20
	 */
	int deleteByParam(String param, Object val);
	
	/**
	 * 根据参数删除表内指定数据,支持集合对象
	 * @author fangzhibin 2015年5月11日 上午9:04:36
	 * @param params 参数map
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月11日 上午9:04:36
	 */
	int deleteByParams(Map<String, Object> params);
	
	/**
	 * 删除表全部数据
	 * @author fangzhibin 2015年3月31日 下午6:04:09
	 * @return 总删除数据条数
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:04:09
	 */
	int deleteAll();
	
	/**
	 * 根据数据库的情况刷新内存中实体的状态，同时覆盖对实体所做的任何修改。
	 * @author fangzhibin 2015年5月7日 下午4:08:26
	 * @param entity
	 * @modify: {原因} by fangzhibin 2015年5月7日 下午4:08:26
	 */
	void refresh(T entity);
	
	/**
	 * 执行flush操作时才会被同步到数据库中。你也可以在任何时候通过调用flush()方法做强行同步。缺省情况下，flush操作会在相关查询执行之前和事务提交之时自动执行（一些低效的Java Persistence实现甚至可能会在任何查询执行之前都做flush操作）
	 * @author fangzhibin 2015年5月7日 下午4:13:25
	 * @modify: {原因} by fangzhibin 2015年5月7日 下午4:13:25
	 */
	void flush();
	
	/**
	 * 所有的托管实体都变成游离对象,建议先进行flush，避免数据丢失
	 * @author fangzhibin 2015年5月7日 下午4:12:25
	 * @modify: {原因} by fangzhibin 2015年5月7日 下午4:12:25
	 */
	void clear();
}
