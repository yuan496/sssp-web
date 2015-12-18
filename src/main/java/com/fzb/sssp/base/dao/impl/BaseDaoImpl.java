package com.fzb.sssp.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fzb.sssp.base.dao.IBaseDao;
import com.fzb.sssp.commons.basic.DBSort;
import com.fzb.sssp.commons.basic.DBType;
import com.fzb.sssp.commons.basic.PageResults;
import com.fzb.sssp.exception.EngineerException;
import com.fzb.sssp.utils.FastJsonTool;
import com.fzb.sssp.utils.GenericsTool;

/**
 * jap的基类
 * @author fangzhibin 2015年4月1日 上午9:13:11
 * @version V1.0
 * @param <T> 实体对象类型
 * @param <ID> 实体主键类型
 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:13:11
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T, ID extends Serializable> implements IBaseDao<T, ID> {
	
	private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	// JPA回话对象
	@PersistenceContext
	protected EntityManager entityManager;
	// 数据库类型
	private DBType dbType = null;
	// 主键名
	private String keyName = null;
	// 实体类对象
	private Class<T> entityClass;
	// sql的order by正则
	private static final Pattern ORDER_PATTERN = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
	
	/**
	 * 获取泛型实体类型
	 * @author fangzhibin 2015年3月31日 下午5:49:39
	 * @return {@link T}的class
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:49:39
	 */
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>)GenericsTool.getSuperClassGenricType(getClass());
		}
		return entityClass;
	}
	
	/**
	 * 获取实体的主键属性名,默认值为"id"
	 * @author fangzhibin 2015年3月31日 下午5:50:23
	 * @return 主键属性名
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午5:50:23
	 */
	protected String getPrimaryKeyName() {
		if (null == keyName) {
			if (null != getEntityClass()) {
				Class<?> clazz = getEntityClass();
				while (null != clazz) {
					Field[] fields = clazz.getDeclaredFields();
					if (null != fields && fields.length > 0) {
						for (Field field : fields) {
							Id key = field.getAnnotation(Id.class);
							if (null != key) {
								keyName = field.getName();
								return keyName;
							}
						}
					}
					clazz = clazz.getSuperclass();
				}
			}
		}
		if (null == keyName) {
			keyName = "id";
		}
		return keyName;
	}
	
	@Override
	public DBType getDialect() {
		if (null == dbType) {
			Map<String, Object> properties = entityManager.getEntityManagerFactory().getProperties();
			Object dialect = properties.get("hibernate.dialect");
			if (null != dialect) {
				String dialectStr = dialect.toString().trim().toLowerCase();
				if (StringUtils.isNotBlank(dialectStr)) {
					if (dialectStr.contains("mysql")) {
						dbType = DBType.MYSQL;
					} else if (dialectStr.contains("oracle")) {
						dbType = DBType.ORACLE;
					} else if (dialectStr.contains("sqlserver")) {
						dbType = DBType.SQLSERVER;
					} else if (dialectStr.contains("postgresql")) {
						dbType = DBType.POSTGRESQL;
					} else if (dialectStr.contains("postgis")) {
						dbType = DBType.POSTGIS;
					} else if (dialectStr.contains("hsql")) {
						dbType = DBType.HSQL;
					} else if (dialectStr.contains("h2")) {
						dbType = DBType.H2;
					} else if (dialectStr.contains("db2")) {
						dbType = DBType.DB2;
					} else if (dialectStr.contains("mariadb")) {
						dbType = DBType.MARIADB;
					} else if (dialectStr.contains("derby")) {
						dbType = DBType.DERBY;
					} else if (dialectStr.contains("informix")) {
						dbType = DBType.INFORMIX;
					} else if (dialectStr.contains("sybase")) {
						dbType = DBType.SYBASE;
					}
				}
			}
		}
		return dbType;
	}
	
	@Override
	public void save(T entity) {
		entityManager.persist(entity);
	}
	
	@Override
	public void saveAll(List<T> entities) {
		if (null != entities && !entities.isEmpty()) {
			for (T entity : entities) {
				save(entity);
			}
		}
	}
	
	@Override
	public T merge(T entity) {
		return entityManager.merge(entity);
	}
	
	@Override
	public void saveOrUpdateAll(List<T> entities) {
		if (null != entities && !entities.isEmpty()) {
			for (T entity : entities) {
				merge(entity);
			}
		}
	}
	
	@Override
	public int update(T entity) {
		ID id = (ID)entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
		if (null != id && null != get(id)) {
			entityManager.merge(entity);
			return 1;
		} else {
			log.warn("以[{}]更新实体[{}]时未找到主键对应数据", FastJsonTool.object2Json(entity), getEntityClass().getName());
		}
		return 0;
	}
	
	@Override
	public void updateAll(List<T> entities) {
		if (null != entities && !entities.isEmpty()) {
			for (T entity : entities) {
				update(entity);
			}
		}
	}
	
	@Override
	public int update(Map<String, Object> params) {
		Object idVal = params != null?params.get(getPrimaryKeyName()):null;
		if (null == idVal || StringUtils.isBlank(idVal.toString())) {
			throw new EngineerException("DB_ENTITY_ID_NULL", "更新" + getEntityClass().getName() + "属性数据入库的主键为空");
		}
		if (null == params || params.size() <= 1) {
			throw new EngineerException("DB_ENTITY_PARAM_NULL", "更新主键为" + idVal.toString() + "的" + getEntityClass().getName() + "入库时属性值参数为空");
		}
		StringBuilder ql = new StringBuilder("update ");
		ql.append(getEntityClass().getName());
		ql.append(" t set ");
		for (Entry<String, Object> entry : params.entrySet()) {
			if (!StringUtils.equals(getPrimaryKeyName(), entry.getKey())) {
				ql.append("t.");
				ql.append(entry.getKey());
				ql.append("=:");
				ql.append(entry.getKey());
				ql.append(",");
			}
		}
		ql.deleteCharAt(ql.length() - 1);
		ql.append(" where t.");
		ql.append(getPrimaryKeyName());
		ql.append("=:");
		ql.append(getPrimaryKeyName());
		return executeQL(ql.toString(), params);
	}
	
	@Override
	public T get(ID id) {
		return entityManager.find(getEntityClass(), id);
	}
	
	@Override
	public List<T> getByIds(List<ID> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return null;
		}
		StringBuilder ql = new StringBuilder("SELECT t FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t");
		ql.append(" WHERE ");
		List<List<ID>> params = new ArrayList<List<ID>>();
		int size = ids.size();
		if (size <= 1000) {
			ql.append(" t.");
			ql.append(getPrimaryKeyName());
			ql.append(" in :ids0");
			params.add(ids);
		} else {
			int num = 1;
			while (true) {
				int endIdx = num * 1000 > size?size:num * 1000;
				List<ID> idList = ids.subList((num - 1) * 1000, endIdx);
				ql.append(" (t.");
				ql.append(getPrimaryKeyName());
				ql.append(" in :ids");
				ql.append((num - 1));
				ql.append(")");
				params.add(idList);
				if (endIdx >= size) {
					break;
				}
				ql.append(" or ");
				num++;
			}
		}
		Query query = entityManager.createQuery(ql.toString());
		int s = params.size();
		for (int i = 0; i < s; i++) {
			List<ID> list = params.get(i);
			query.setParameter("ids" + i, list);
		}
		return query.getResultList();
	}
	
	@Override
	public T getUnique(String param, Object val) {
		if (StringUtils.isBlank(param)) {
			return null;
		}
		StringBuilder ql = new StringBuilder("SELECT t FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t");
		ql.append(" WHERE t.");
		ql.append(param);
		if (null == val) {
			ql.append(" is null ");
		} else {
			if (val instanceof Collection) {
				ql.append(" in :");
			} else {
				ql.append(" = :");
			}
			ql.append(StringUtils.replace(param, ".", "_"));
		}
		Query query = entityManager.createQuery(ql.toString());
		if (null != val) {
			query.setParameter(StringUtils.replace(param, ".", "_"), val);
		}
		return (T)query.getSingleResult();
	}
	
	@Override
	public T getUnique(Map<String, Object> params) {
		StringBuilder ql = new StringBuilder("SELECT t FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t");
		boolean first = true;
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey())) {
					continue;
				}
				if (first) {
					ql.append(" WHERE ");
					first = false;
				} else {
					ql.append(" and ");
				}
				ql.append("t.");
				ql.append(entry.getKey());
				if (null == entry.getValue()) {
					ql.append(" is null ");
				} else {
					if (entry.getValue() instanceof Collection) {
						ql.append(" in :");
					} else {
						ql.append(" = :");
					}
					ql.append(StringUtils.replace(entry.getKey(), ".", "_"));
				}
			}
		}
		Query query = entityManager.createQuery(ql.toString());
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(StringUtils.replace(entry.getKey(), ".", "_"), entry.getValue());
			}
		}
		return (T)query.getSingleResult();
	}
	
	@Override
	public List<T> getAll(DBSort sort) {
		return getListByQL("SELECT t FROM " + getEntityClass().getName() + " t ", null, sort);
	}
	
	@Override
	public long getRowCount() {
		String hql = "select count(o) from " + getEntityClass().getName() + " o";
		Long count = (Long)findUniqueByQL(hql, null);
		return count.longValue();
	}
	
	@Override
	public List<T> findByParam(String param, Object val, DBSort sort) {
		if (StringUtils.isBlank(param)) {
			return null;
		}
		if (null != sort) {
			sort.setAlias("t");
		}
		StringBuilder ql = new StringBuilder("SELECT t FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t WHERE t.");
		ql.append(param);
		if (null == val) {
			ql.append(" is null ");
		} else {
			if (val instanceof Collection) {
				ql.append(" in :");
			} else {
				ql.append(" = :");
			}
			ql.append(StringUtils.replace(param, ".", "_"));
		}
		ql.append(" ");
		ql.append(sort == null?"":sort.sort());
		Query query = entityManager.createQuery(ql.toString());
		if (null != val) {
			query.setParameter(StringUtils.replace(param, ".", "_"), val);
		}
		return query.getResultList();
	}
	
	@Override
	public List<T> findByParams(Map<String, Object> params, DBSort sort) {
		if (null != sort) {
			sort.setAlias("t");
		}
		StringBuilder ql = new StringBuilder("SELECT t FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t");
		boolean first = true;
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey())) {
					continue;
				}
				if (first) {
					ql.append(" WHERE ");
					first = false;
				} else {
					ql.append(" and ");
				}
				ql.append("t.");
				ql.append(entry.getKey());
				if (null == entry.getValue()) {
					ql.append(" is null ");
				} else {
					if (entry.getValue() instanceof Collection) {
						ql.append(" in :");
					} else {
						ql.append(" = :");
					}
					ql.append(StringUtils.replace(entry.getKey(), ".", "_"));
				}
			}
		}
		if (null != sort) {
			ql.append(" ");
			ql.append(sort.sort());
		}
		Query query = entityManager.createQuery(ql.toString());
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(StringUtils.replace(entry.getKey(), ".", "_"), entry.getValue());
			}
		}
		return query.getResultList();
	}
	
	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}
	
	@Override
	public void deletes(Collection<T> entities) {
		for (T entity : entities) {
			delete(entity);
		}
	}
	
	@Override
	public boolean deleteById(ID id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(getPrimaryKeyName(), id);
		int count = executeQL("DELETE FROM " + getEntityClass().getName() + " t WHERE t." + getPrimaryKeyName() + " = :" + getPrimaryKeyName(), params);
		return count > 0?true:false;
	}
	
	@Override
	public int deleteByIds(List<ID> ids) {
		int count = 0;
		if (null != ids && !ids.isEmpty()) {
			int size = ids.size();
			String ql = "DELETE FROM " + getEntityClass().getName() + " t WHERE t." + getPrimaryKeyName() + " in :ids";
			if (size <= 1000) {
				Query query = entityManager.createQuery(ql);
				query.setParameter("ids", ids);
				count += query.executeUpdate();
			} else {
				int num = 1;
				while (true) {
					int endIdx = num * 1000 > size?size:num * 1000;
					List<ID> idList = ids.subList((num - 1) * 1000, endIdx);
					Query query = entityManager.createQuery(ql);
					query.setParameter("ids", idList);
					count += query.executeUpdate();
					if (endIdx >= size) {
						break;
					}
					num++;
				}
			}
		}
		return count;
	}
	
	@Override
	public int deleteByParam(String param, Object val) {
		if (StringUtils.isBlank(param)) {
			return 0;
		}
		StringBuilder ql = new StringBuilder("DELETE FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t ");
		ql.append(" WHERE t.");
		ql.append(param);
		if (null == val) {
			ql.append(" is null ");
		} else {
			if (val instanceof Collection) {
				ql.append(" in :");
			} else {
				ql.append(" = :");
			}
			ql.append(StringUtils.replace(param, ".", "_"));
		}
		Query query = entityManager.createQuery(ql.toString());
		if (null != val) {
			query.setParameter(StringUtils.replace(param, ".", "_"), val);
		}
		return query.executeUpdate();
	}
	
	@Override
	public int deleteByParams(Map<String, Object> params) {
		StringBuilder ql = new StringBuilder("DELETE FROM ");
		ql.append(getEntityClass().getName());
		ql.append(" t ");
		boolean first = true;
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey())) {
					continue;
				}
				if (first) {
					ql.append(" WHERE ");
					first = false;
				} else {
					ql.append(" and ");
				}
				ql.append("t.");
				ql.append(entry.getKey());
				if (null == entry.getValue()) {
					ql.append(" is null ");
				} else {
					if (entry.getValue() instanceof Collection) {
						ql.append(" in :");
					} else {
						ql.append(" = :");
					}
					ql.append(StringUtils.replace(entry.getKey(), ".", "_"));
				}
			}
		}
		Query query = entityManager.createQuery(ql.toString());
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(StringUtils.replace(entry.getKey(), ".", "_"), entry.getValue());
			}
		}
		return query.executeUpdate();
	}
	
	@Override
	public int deleteAll() {
		Query query = entityManager.createQuery("DELETE FROM " + getEntityClass().getName() + " t");
		return query.executeUpdate();
	}
	
	@Override
	public void refresh(T entity) {
		entityManager.refresh(entity);
	}
	
	@Override
	public void flush() {
		entityManager.flush();
	}
	
	@Override
	public void clear() {
		entityManager.clear();
	}
	
	/**
	 * 根据ql语句和参数执行
	 * @author fangzhibin 2015年5月10日 上午10:36:56
	 * @param ql jpa语句,语句要求使用别名
	 * @param params 参数
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月10日 上午10:36:56
	 */
	protected int executeQL(String ql, Map<String, Object> params) {
		Query query = entityManager.createQuery(ql);
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.executeUpdate();
	}
	
	/**
	 * 根据sql语句和参数执行
	 * @author fangzhibin 2015年5月10日 上午10:36:56
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月10日 上午10:36:56
	 */
	protected int executeSQL(String sql, Map<String, Object> params) {
		String sqlStr = sql;
		List<Object> values = new ArrayList<Object>();
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				sqlStr = sqlStr.replace(":" + entry.getKey(), "?");
				values.add(entry.getValue());
			}
		}
		Query query = entityManager.createNativeQuery(sqlStr);
		if (!values.isEmpty()) {
			int size = values.size();
			for (int i = 0; i < size; i++) {
				query.setParameter(i + 1, values.get(i));
			}
		}
		return query.executeUpdate();
	}
	
	/**
	 * 根据ql查询唯一实体，可能出现{@link NonUniqueResultException} 或 {@link NoResultException}异常
	 * @author fangzhibin 2015年3月31日 下午6:06:01
	 * @param ql ql语句,语句要求使用别名
	 * @param params 参数
	 * @return 唯一实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:06:01
	 */
	protected T getUniqueByQL(String ql, Map<String, Object> params) {
		Query query = entityManager.createQuery(ql);
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return (T)query.getSingleResult();
	}
	
	/**
	 * 根据ql查询唯一实体，可能出现{@link NonUniqueResultException} 或 {@link NoResultException}异常
	 * @author fangzhibin 2015年3月31日 下午6:06:01
	 * @param ql ql语句,语句要求使用别名
	 * @param params 参数
	 * @return 唯一实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:06:01
	 */
	protected Object findUniqueByQL(String ql, Map<String, Object> params) {
		Query query = entityManager.createQuery(ql);
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getSingleResult();
	}
	
	/**
	 * 根据sql查询唯一实体，可能出现{@link NonUniqueResultException} 或 {@link NoResultException}异常
	 * @author fangzhibin 2015年3月31日 下午6:06:01
	 * @param sql sql语句
	 * @param params 参数
	 * @return 唯一实体对象
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:06:01
	 */
	protected Object getUniqueBySQL(String sql, Map<String, Object> params) {
		String sqlStr = sql;
		List<Object> values = new ArrayList<Object>();
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				sqlStr = sqlStr.replace(":" + entry.getKey(), "?");
				values.add(entry.getValue());
			}
		}
		Query query = entityManager.createNativeQuery(sqlStr);
		if (!values.isEmpty()) {
			int size = values.size();
			for (int i = 0; i < size; i++) {
				query.setParameter(i + 1, values.get(i));
			}
		}
		List<?> result = query.getResultList();
		if (null == result || result.isEmpty()) {
			throw new NoResultException("sql语句=[" + sql + "],参数=" + FastJsonTool.object2Json(params) + "没有结果");
		} else if (result.size() > 1) {
			throw new NonUniqueResultException("sql语句=[" + sql + "],参数=" + FastJsonTool.object2Json(params) + "结果不唯一");
		} else {
			return result.get(0);
		}
	}
	
	/**
	 * 根据QL语句，得到对应的list
	 * @author fangzhibin 2015年3月31日 下午6:10:13
	 * @param ql ql语句,语句要求使用别名
	 * @param params 参数
	 * @param sort 排序对象,null则表示默认排序
	 * @return 实体集合
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:10:13
	 */
	protected List<T> getListByQL(String ql, Map<String, Object> params, DBSort sort) {
		Query query = entityManager.createQuery(ql + " " + (sort == null?"":sort.sort()));
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getResultList();
	}
	
	/**
	 * 根据QL语句，查询对应的数据
	 * @author fangzhibin 2015年5月9日 下午4:32:50
	 * @param ql ql语句,语句要求使用别名
	 * @param params 参数
	 * @param sort 排序对象,null则表示默认排序
	 * @return 数组集合
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午4:32:50
	 */
	protected List<?> findListByQL(String ql, Map<String, Object> params, DBSort sort) {
		Query query = entityManager.createQuery(ql + " " + (sort == null?"":sort.sort()));
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getResultList();
	}
	
	/**
	 * 根据SQL语句，得到对应的list,实体对象类型未知
	 * @author fangzhibin 2015年3月31日 下午6:10:53
	 * @param sql sql语句
	 * @param params 参数
	 * @param sort 排序对象,null则表示默认排序
	 * @return 数据集合
	 * @modify: {原因} by fangzhibin 2015年3月31日 下午6:10:53
	 */
	protected List<?> findListBySQL(String sql, Map<String, Object> params, DBSort sort) {
		String sqlStr = sql;
		List<Object> values = new ArrayList<Object>();
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				sqlStr = sqlStr.replace(":" + entry.getKey(), "?");
				values.add(entry.getValue());
			}
		}
		Query query = entityManager.createNativeQuery(sqlStr + (sort == null?"":sort.sort()));
		if (!values.isEmpty()) {
			int size = values.size();
			for (int i = 0; i < size; i++) {
				query.setParameter(i + 1, values.get(i));
			}
		}
		return query.getResultList();
	}
	
	/**
	 * 根据QL语句分页查询数据
	 * @author fangzhibin 2015年4月1日 上午9:15:01
	 * @param ql QL语句,语句要求使用别名
	 * @param countQL 查询记录条数的QL语句
	 * @param pageNo 下一页
	 * @param pageSize 一页总条数
	 * @param params 参数
	 * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
	 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:15:01
	 */
	protected PageResults<T> findPageByFetchedQL(String ql, String countQL, int pageNo, int pageSize, Map<String, Object> params) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = entityManager.createQuery(ql);
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		int currentPage = pageNo > 1?pageNo:1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		if (StringUtils.isBlank(countQL)) {
			List<?> countList = findListByQL(buildCountQL(ql), params, null);
			int totalCount = 0;
			if (null != countList && countList.size() == 1) {
				totalCount = NumberUtils.toInt(countList.get(0).toString());
			}
			retValue.setTotalCount(totalCount);// 设置总记录数
		} else {
			Long count = (Long)getUniqueByQL(countQL, params);
			if (null != count) {
				retValue.setTotalCount(count.intValue());
			}
		}
		retValue.resetPageNo();
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).getResultList();
		if (null == itemList) {
			itemList = new ArrayList<T>();
		}
		retValue.setResults(itemList);
		return retValue;
	}
	
	/**
	 * 根据SQL语句分页查询数据
	 * @author fangzhibin 2015年4月1日 上午9:15:01
	 * @param sql SQL语句
	 * @param countSQL 查询记录条数的SQL语句
	 * @param pageNo 下一页
	 * @param pageSize 一页总条数
	 * @param params 参数
	 * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
	 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:15:01
	 */
	protected PageResults<?> findPageByFetchedSQL(String sql, String countSQL, int pageNo, int pageSize, Map<String, Object> params) {
		PageResults<Object> retValue = new PageResults<Object>();
		String sqlStr = sql;
		List<Object> values = new ArrayList<Object>();
		if (null != params && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				if (StringUtils.isBlank(entry.getKey()) || null == entry.getValue()) {
					continue;
				}
				sqlStr = sqlStr.replace(":" + entry.getKey(), "?");
				values.add(entry.getValue());
			}
		}
		Query query = entityManager.createNativeQuery(sqlStr);
		if (!values.isEmpty()) {
			int size = values.size();
			for (int i = 0; i < size; i++) {
				query.setParameter(i + 1, values.get(i));
			}
		}
		int currentPage = pageNo > 1?pageNo:1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		if (StringUtils.isBlank(countSQL)) {
			List<?> countList = findListBySQL(buildCountQL(sqlStr), params, null);
			int totalCount = 0;
			if (null != countList && countList.size() == 1) {
				totalCount = NumberUtils.toInt(countList.get(0).toString());
			}
			retValue.setTotalCount(totalCount);// 设置总记录数
		} else {
			Long count = (Long)getUniqueBySQL(countSQL, params);
			if (null != count) {
				retValue.setTotalCount(count.intValue());
			}
		}
		retValue.resetPageNo();
		List<Object> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).getResultList();
		if (null == itemList) {
			itemList = new ArrayList<Object>();
		}
		retValue.setResults(itemList);
		return retValue;
	}
	
	/**
	 * 构建统计的查询语句
	 * @author fangzhibin 2015年5月9日 下午4:59:28
	 * @param ql
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午4:59:28
	 */
	private String buildCountQL(String ql) {
		String q = "SELECT COUNT(1) " + ql.substring(ql.toLowerCase().indexOf("from"));
		int g = ql.toLowerCase().lastIndexOf("group by");
		int k = ql.lastIndexOf(")");
		if (k < g) {
			return removeQLOrders(q.substring(0, q.toLowerCase().lastIndexOf("group by")));
		} else {
			return removeQLOrders(q);
		}
	}
	
	/**
	 * 去掉ql的order字句，用于count查询
	 * @author fangzhibin 2015年5月9日 下午4:56:12
	 * @param ql
	 * @return
	 * @modify: {原因} by fangzhibin 2015年5月9日 下午4:56:12
	 */
	private String removeQLOrders(String ql) {
		Matcher m = ORDER_PATTERN.matcher(ql);
		StringBuffer qlBuf = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(qlBuf, "");
		}
		m.appendTail(qlBuf);
		return qlBuf.toString();
	}
}
