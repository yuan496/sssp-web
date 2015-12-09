package com.fzb.sssp.service;

import org.springframework.data.domain.Page;
import com.fzb.sssp.domain.Permission;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:30:50
 */
public interface PermissionService {
	
	public Page<Permission> getPage(int pageNo, int pageSize);
	
	public void save(Permission permission);
	
	public Permission get(Long id);
	
	public void delete(Long id);
}
