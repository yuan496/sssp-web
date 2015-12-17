package com.fzb.sssp.base.dao.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fzb.sssp.base.dao.IBaseDao;

/**
 * @author fangzhibin 2015-12-17 下午11:12:42
 * @version V1.0
 * @modify: {原因} by fangzhibin 2015-12-17 下午11:12:42
 */
@Transactional
public abstract class BaseDaoImpl implements IBaseDao {
	
	private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@PersistenceContext
	protected EntityManager em;
	
	public void clear() {
		em.clear();
	}
	
	public <T> void delete(Class<T> entityClass, Object entityid) {
		delete(entityClass, new Object[] {entityid});
	}
	
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		for (Object id : entityids) {
			em.remove(em.getReference(entityClass, id));
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> T find(Class<T> entityClass, Object entityId) {
		return em.find(entityClass, entityId);
	}
	
	public void save(Object entity) {
		em.persist(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> long getCount(Class<T> entityClass) {
		return (Long)em.createQuery("select count(" + getCountField(entityClass) + ") from " + getEntityName(entityClass) + " o").getSingleResult();
	}
	
	public void update(Object entity) {
		em.merge(entity);
	}
	
	protected void setQueryParams(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i + 1, queryParams[i]);
			}
		}
	}
	
	/**
	 * 组装order by语句
	 * @param orderby
	 * @return
	 */
	protected String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyql = new StringBuffer("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}
	
	/**
	 * 获取实体的名称
	 * @param <T>
	 * @param entityClass 实体类
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass) {
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}
	
	protected <T> String getCountField(Class<T> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null && method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o." + propertydesc.getName() + "." + (!ps[1].getName().equals("class")?ps[1].getName():ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return out;
	}
}
