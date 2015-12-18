package com.fzb.sssp.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import com.fzb.sssp.base.dao.impl.hibernate.HibernateBaseDaoImpl;
import com.fzb.sssp.commons.basic.DBType;
import com.fzb.sssp.dao.SimpleDao;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月18日 上午9:28:14
 */
@Repository
public class SimpleDaoImpl extends HibernateBaseDaoImpl<Object, Long> implements SimpleDao {
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:10:26
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:10:26
	 */
	@Override
	public DBType getCurrentDialect() {
		return getDialect();
	}
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:10:26
	 * @param obj
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:10:26
	 */
	@Override
	public void saveEntity(Object obj) {
		getSession().save(obj);
	}
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:10:26
	 * @param sql
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:10:26
	 */
	@Override
	public void executeSql(final String sql) {
		// 执行JDBC的数据批量保存
		Work jdbcWork = new Work() {
			
			public void execute(Connection connection) throws SQLException {
				Statement statement = null;
				try {
					statement = connection.createStatement();
					statement.execute(sql);
				} finally {
					if (statement != null) {
						statement.close();
					}
				}
			}
		};
		getSession().doWork(jdbcWork);
	}
}
