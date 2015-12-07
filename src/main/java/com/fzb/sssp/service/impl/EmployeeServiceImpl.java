package com.fzb.sssp.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fzb.sssp.dao.EmployeeRepository;
import com.fzb.sssp.domain.Employee;
import com.fzb.sssp.service.EmployeeService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:31:48
 */
@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年10月6日 下午12:34:37
     * @param pageNo
     * @param pageSize
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年10月6日 下午12:34:37
     */
    @Override
    public Page<Employee> getPage(int pageNo, int pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize);
	    return employeeRepository.findAll(pageRequest);
    }
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年10月6日 下午7:43:37
	 * @param employee
	 * @modify {上次修改原因} by fangzhibin 2015年10月6日 下午7:43:37
	 */
	@Override
	public void save(Employee employee) {
		log.info("save the employee is {}", employee.toString());
		if(null != employee && null == employee.getId()) {
			employee.setCreateTime(new Date());
		}
		employeeRepository.saveAndFlush(employee);
	}

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年10月6日 下午9:40:15
     * @param id
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年10月6日 下午9:40:15
     */
    @Override
    public Employee get(Integer id) {
    	log.info("get the employee by id is {}", id);
	    return employeeRepository.findOne(id);
    }
    
    /**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年10月6日 下午11:08:07
     * @param id
     * @modify {上次修改原因} by fangzhibin 2015年10月6日 下午11:08:07
     */
    @Override
    public void delete(Integer id) {
    	log.info("delete the employee by id is {}", id);
    	employeeRepository.delete(id);
    }
}
