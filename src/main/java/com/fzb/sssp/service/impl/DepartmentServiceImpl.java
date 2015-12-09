package com.fzb.sssp.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fzb.sssp.dao.DepartmentRepository;
import com.fzb.sssp.domain.Department;
import com.fzb.sssp.service.DepartmentService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午6:54:48
 */
@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	
	private static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年10月6日 下午6:54:48
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年10月6日 下午6:54:48
	 */
	@Override
	public List<Department> getAll() {
		log.info("findAll department ...");
		return departmentRepository.getAll();
	}

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
    public Page<Department> getPage(int pageNo, int pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize);
	    return departmentRepository.findAll(pageRequest);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param department
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void save(Department department) {
    	log.info("save the department is {}", department.toString());
		if(null != department && null == department.getId()) {
			department.setCreateTime(new Date());
		}
		departmentRepository.saveAndFlush(department);
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
    public Department get(Long id) {
    	log.info("get the department by id is {}", id);
	    return departmentRepository.findOne(id);
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
    	log.info("delete the department by id is {}", id);
    	departmentRepository.delete(id);
    }
}
