package com.fzb.sssp.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户缓存信息监听类
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 上午10:44:08
 */
public class EngineerSessionListener implements HttpSessionListener {
	
	private static final Logger log = LoggerFactory.getLogger(EngineerSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.debug("EngineerSessionListener sessionCreated sessionId:" + event.getSession().getId());
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		event.getSession().invalidate();
		log.debug("EngineerSessionListener sessionDestroyed invalidate");
	}
}
