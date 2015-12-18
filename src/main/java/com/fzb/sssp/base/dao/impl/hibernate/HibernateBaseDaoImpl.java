package com.fzb.sssp.base.dao.impl.hibernate;

import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;
import com.fzb.sssp.base.dao.impl.BaseDaoImpl;

/**
 * hibernate的基类
 * @author fangzhibin 2015年4月1日 上午9:13:11
 * @version V1.0
 * @param <T>
 * @param <ID>
 * @modify: {原因} by fangzhibin 2015年4月1日 上午9:13:11
 */
public class HibernateBaseDaoImpl<T, ID extends Serializable> extends BaseDaoImpl<T, ID> {
	
	/**
	 * 获取hibernate的session对象
	 * @author fangzhibin 2015年10月23日 下午3:43:23
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月23日 下午3:43:23
	 */
	protected Session getSession() {
		// 需要开启事物，才能得到CurrentSession
		return entityManager.unwrap(Session.class);
	}
	
	/**
	 * 根据hql和参数更新数据
	 * @author fangzhibin 2015年10月23日 下午3:43:35
	 * @param hql
	 * @param values
	 * @return
	 * @modify: {原因} by fangzhibin 2015年10月23日 下午3:43:35
	 */
	protected int updateByHQL(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.executeUpdate();
	}
}
