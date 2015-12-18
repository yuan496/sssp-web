package com.fzb.sssp.commons.basic;

/**
 * 数据库类型枚举
 * @author fangzhibin 2014年5月16日 下午4:21:37
 * @version V1.0   
 * @modify: {原因} by fangzhibin 2014年5月16日 下午4:21:37
 */
public enum DBType {
	DB2,
	DERBY,
	HSQL,
	INFORMIX,
	MYSQL,
	MARIADB,
	ORACLE,
	POSTGRESQL,
	POSTGIS,
	SQLSERVER,
	SYBASE,
	H2,
	/**
	 * 未匹配数据库类型
	 */
	EMPTY
}
