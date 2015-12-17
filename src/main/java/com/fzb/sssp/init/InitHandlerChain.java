package com.fzb.sssp.init;

import java.util.List;
import javax.annotation.PostConstruct;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月17日 下午9:01:57
 */
public class InitHandlerChain {
	
	private List<InitHandler> handlers;
	
	public List<InitHandler> getHandlers() {
		return handlers;
	}
	
	public void setHandlers(List<InitHandler> handlers) {
		this.handlers = handlers;
	}
	
	@PostConstruct
	public void doInit() throws Exception {
		for (InitHandler handler : this.handlers) {
			handler.doInit();
		}
	}
}
