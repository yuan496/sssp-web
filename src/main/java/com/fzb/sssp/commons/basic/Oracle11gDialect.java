package com.fzb.sssp.commons.basic;

import java.sql.Types;
import org.hibernate.dialect.Oracle10gDialect;

/**
 * 把text转换为clob型
 * @author fangzhibin 2015年5月11日 下午12:46:21
 * @version V1.0   
 * @modify: {原因} by fangzhibin 2015年5月11日 下午12:46:21
 */
public class Oracle11gDialect extends Oracle10gDialect {
	@Override
	protected void registerLargeObjectTypeMappings() {
		registerColumnType(Types.BINARY, 2000, "raw($l)");
		registerColumnType(Types.BINARY, "long raw");

		registerColumnType(Types.VARBINARY, 2000, "raw($l)");
		registerColumnType(Types.VARBINARY, "long raw");

		registerColumnType(Types.BLOB, "blob");
		registerColumnType(Types.CLOB, "clob");

		registerColumnType(Types.LONGVARCHAR, "clob");
		registerColumnType(Types.LONGVARBINARY, "long raw");
	}
}
