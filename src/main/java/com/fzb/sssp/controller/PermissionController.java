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
import com.fzb.sssp.entity.Permission;
import com.fzb.sssp.service.PermissionService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@ModelAttribute
	public void getPermission(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
		if (null != id) {
			Permission permission = permissionService.get(id);
			map.put("permission", permission);
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
		Page<Permission> page = permissionService.getPage(pageNo, 5);
		map.put("page", page);
		return "permission/list";
	}
	
	@RequestMapping(value = "/permission", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("permission", new Permission());
		return "permission/input";
	}
	
	@RequestMapping(value = "/permission", method = RequestMethod.POST)
	public String save(Permission permission) {
		permissionService.save(permission);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map) {
		Permission permission = permissionService.get(id);
		map.put("permission", permission);
		return "permission/input";
	}
	
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
	public String update(Permission permission) {
		permissionService.save(permission);
		return "redirect:/permission/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Long id) {
		permissionService.delete(id);
		return "redirect:/permission/list";
	}
}
