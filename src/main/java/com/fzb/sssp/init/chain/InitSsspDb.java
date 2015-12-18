package com.fzb.sssp.init.chain;

import java.io.File;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import com.fzb.sssp.commons.constants.EngineerConstant;
import com.fzb.sssp.entity.User;
import com.fzb.sssp.init.InitHandler;
import com.fzb.sssp.service.SimpleService;
import com.fzb.sssp.service.UserService;
import com.fzb.sssp.utils.SaxXmlUtils;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月18日 上午9:22:46
 */
@Component("initSsspDb")
public class InitSsspDb implements InitHandler {
	
	private static Logger logger = LoggerFactory.getLogger(InitSsspDb.class);
	// 数据库初始化配置文件路径
	private final static String SSSP_DB_FILE = "config/sssp-db.xml";
	@Autowired
	private SimpleService simpleService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 初始化.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年12月18日 上午10:12:54
	 * @throws Exception
	 * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:12:54
	 */
	@Override
	public void doInit() throws Exception {
		List<User> users = userService.findByCode(EngineerConstant.ADMIN_CODE);
		if(null != users && !users.isEmpty()) {
			logger.info("db has initial, don't need to initial!");
		} else {
			String basePath = this.getClass().getResource("/").getPath();
			createDataBase(basePath); // 导入默认数据
			logger.info("db initial success!");
		}
	}
	
	/**
	 * 导入默认数据
	 * @author fangzhibin 2015年11月13日 下午4:52:41
	 * @param basePath
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @modify: {原因} by fangzhibin 2015年11月13日 下午4:52:41
	 */
	private void createDataBase(String basePath) throws Exception {
		String dbConfigFileName = basePath + SSSP_DB_FILE;
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		SaxXmlUtils saxXmlUtils = new SaxXmlUtils();
		File file = new File(dbConfigFileName);
		if(file.exists() && file.isFile()) {
			parser.parse(file, saxXmlUtils);
			List<Object> list = saxXmlUtils.getList();
			simpleService.saveAllIgnoreFailure(list);
		}
	}
}
