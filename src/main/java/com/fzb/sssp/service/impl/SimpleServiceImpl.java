package com.fzb.sssp.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fzb.sssp.commons.basic.DBType;
import com.fzb.sssp.dao.SimpleDao;
import com.fzb.sssp.service.SimpleService;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月18日 上午9:26:51
 */
@Service
@Transactional
public class SimpleServiceImpl implements SimpleService {
	
	private static Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);
	@Autowired
	private SimpleDao simpleDao;
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:14:46
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:14:46
	 */
	@Override
	public DBType getCurrentDialect() {
		return simpleDao.getCurrentDialect();
	}
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:14:46
	 * @param fileName
	 * @param dbType
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:14:46
	 */
	@Override
	public boolean excuteSqlFile(String fileName, DBType dbType) {
		Boolean sqlFlag = true;
		List<String> sqlList = new ArrayList<String>();
		if (dbType == DBType.ORACLE) {
			readOracleFile(fileName, sqlList);
		} else if (dbType == DBType.POSTGIS) {
			readPostgresFile(fileName, sqlList);
		} else {
			logger.error("数据库连接出错，暂不支持除Oracle和Postgresql以外的其他数据库！");
			return false;
		}
		for (String sql : sqlList) {
			try {
				simpleDao.executeSql(sql);
			} catch (Exception e) {
				logger.error(e.getMessage() + " \n sql语句: " + sql);
				continue;
			}
		}
		return sqlFlag;
	}
	
	/**
	 * 读取oracle数据库脚本
	 * @author caoyunfei 2015年11月13日 下午1:46:11
	 * @param fileName
	 * @param sqlList
	 * @modify: {原因} by caoyunfei 2015年11月13日 下午1:46:11
	 */
	private void readOracleFile(String fileName, List<String> sqlList) {
		StringBuffer temp = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getResourceAsStream(fileName), "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if (str.startsWith("--")) {
					continue;
				}
				temp.append(str);
				while (temp.charAt(temp.length() - 1) == ' ') {
					temp.deleteCharAt(temp.length() - 1);
				}
				if (temp.lastIndexOf(";") == temp.length() - 1 && temp.indexOf("declare") == -1 && temp.indexOf(" function ") == -1 && temp.indexOf(" procedure ") == -1 && temp.indexOf(" package ") == -1
				        && temp.indexOf("begin") == -1) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if (str.equals("/") && (temp.indexOf("declare") != -1 || temp.indexOf(" function ") != -1 || temp.indexOf(" procedure ") != -1 || temp.indexOf(" package ") != -1 || temp.indexOf("begin") != -1)) {
					sqlList.add(temp.substring(0, temp.length() - 2));
					temp = new StringBuffer();
				} else {
					temp.append('\n');
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 读取Postgres数据库脚本
	 * @author caoyunfei 2015年11月13日 下午1:46:19
	 * @param fileName
	 * @param sqlList
	 * @modify: {原因} by caoyunfei 2015年11月13日 下午1:46:19
	 */
	private void readPostgresFile(String fileName, List<String> sqlList) {
		StringBuffer temp = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getResourceAsStream(fileName), "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if (str.startsWith("--")) {
					continue;
				}
				temp.append(str);
				if (temp.indexOf(";") != -1 && temp.indexOf("DECLARE") == -1 && temp.indexOf(" FUNCTION ") == -1 && temp.indexOf(" PROCEDURE ") == -1) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if (temp.indexOf("plpgsql") != -1 && (temp.indexOf("DECLARE") != -1 || temp.indexOf(" FUNCTION ") != -1 || temp.indexOf(" PROCEDURE ") != -1)) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else {
					temp.append(System.getProperty("line.separator"));
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	private InputStream getResourceAsStream(String resource) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(new File(resource));
		} catch (FileNotFoundException e) {
			logger.error(resource + " not found");
		}
		return stream;
	}
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:14:46
	 * @param list
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:14:46
	 */
	@Override
	public void saveAllIgnoreFailure(List<Object> list) {
		if (null != list && !list.isEmpty()) {
			for (Object t : list) {
				try {
					save(t);
				} catch (Exception e) {
					logger.warn("Initilization data [{}] insert error. ", t);
				}
			}
		}
	}
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:14:46
	 * @param object
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:14:46
	 */
	@Override
	public void save(Object object) {
		simpleDao.saveEntity(object);
	}
}
