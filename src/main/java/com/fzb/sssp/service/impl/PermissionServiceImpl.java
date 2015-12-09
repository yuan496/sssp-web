package com.fzb.sssp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fzb.sssp.dao.PermissionRepository;
import com.fzb.sssp.domain.Permission;
import com.fzb.sssp.service.PermissionService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午6:54:48
 */
@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param pageNo
     * @param pageSize
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public Page<Permission> getPage(int pageNo, int pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize);
	    return permissionRepository.findAll(pageRequest);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param user
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void save(Permission permission) {
    	log.info("save the permission is {}", permission.toString());
		permissionRepository.saveAndFlush(permission);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param id
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public Permission get(Long id) {
    	log.info("get the permission by id is {}", id);
	    return permissionRepository.findOne(id);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param id
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void delete(Long id) {
    	log.info("delete the permission by id is {}", id);
    	permissionRepository.delete(id);
    }
}
