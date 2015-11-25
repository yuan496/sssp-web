package com.fzb.sssp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
		return departmentRepository.getAll();
	}
}
