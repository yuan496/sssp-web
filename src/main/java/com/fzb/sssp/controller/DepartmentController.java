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
import com.fzb.sssp.domain.Department;
import com.fzb.sssp.service.DepartmentService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/dept")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@ModelAttribute
	public void getDepartment(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (null != id) {
			Department department = departmentService.get(id);
			map.put("department", department);
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
		Page<Department> page = departmentService.getPage(pageNo, 5);
		map.put("page", page);
		return "dept/list";
	}
	
	@RequestMapping(value = "/dept", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("department", new Department());
		return "dept/input";
	}
	
	@RequestMapping(value = "/dept", method = RequestMethod.POST)
	public String save(Department department) {
		departmentService.save(department);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/dept/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		Department department = departmentService.get(id);
		map.put("department", department);
		return "dept/input";
	}
	
	@RequestMapping(value = "/dept/{id}", method = RequestMethod.PUT)
	public String update(Department department) {
		departmentService.save(department);
		return "redirect:/dept/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Integer id) {
		departmentService.delete(id);
		return "redirect:/dept/list";
	}
}
