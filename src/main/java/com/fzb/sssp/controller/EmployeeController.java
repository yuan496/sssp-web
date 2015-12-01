package com.fzb.sssp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.fzb.sssp.domain.Employee;
import com.fzb.sssp.service.DepartmentService;
import com.fzb.sssp.service.EmployeeService;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:35:39
 */
@Controller
@RequestMapping("/emp")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;
	
	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (null != id) {
			Employee employee = employeeService.get(id);
			employee.setDepartment(null);
			map.put("employee", employee);
		}
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr, Map<String, Object> map) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
			if (pageNo < 1) {
				pageNo = 1;
			}
		} catch (NumberFormatException e) {
		}
		Page<Employee> page = employeeService.getPage(pageNo, 5);
		map.put("page", page);
		return "emp/list";
	}
	
	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentService.getAll());
		map.put("employee", new Employee());
		return "emp/input";
	}
	
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(Employee employee) {
		employeeService.save(employee);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		Employee employee = employeeService.get(id);
		map.put("employee", employee);
		map.put("departments", departmentService.getAll());
		return "emp/input";
	}
	
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.PUT)
	public String update(Employee employee) {
		employeeService.save(employee);
		return "redirect:/web/emp/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Integer id) {
		employeeService.delete(id);
		return "redirect:/web/emp/list";
	}
}
