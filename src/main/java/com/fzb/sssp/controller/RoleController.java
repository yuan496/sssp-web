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
import com.fzb.sssp.domain.Role;
import com.fzb.sssp.service.RoleService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@ModelAttribute
	public void getRole(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
		if (null != id) {
			Role role = roleService.get(id);
			map.put("role", role);
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
		Page<Role> page = roleService.getPage(pageNo, 5);
		map.put("page", page);
		return "role/list";
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("role", new Role());
		return "role/input";
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public String save(Role role) {
		roleService.save(role);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map) {
		Role role = roleService.get(id);
		map.put("role", role);
		return "emp/input";
	}
	
	@RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
	public String update(Role role) {
		roleService.save(role);
		return "redirect:/role/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Long id) {
		roleService.delete(id);
		return "redirect:/role/list";
	}
}
