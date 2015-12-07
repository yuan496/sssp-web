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
import com.fzb.sssp.domain.Menu;
import com.fzb.sssp.service.MenuService;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@ModelAttribute
	public void getMenu(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (null != id) {
			Menu menu = menuService.get(id);
			map.put("menu", menu);
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
		Page<Menu> page = menuService.getPage(pageNo, 5);
		map.put("page", page);
		return "menu/list";
	}
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("menu", new Menu());
		return "menu/input";
	}
	
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	public String save(Menu menu) {
		menuService.save(menu);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		Menu menu = menuService.get(id);
		map.put("menu", menu);
		return "menu/input";
	}
	
	@RequestMapping(value = "/menu/{id}", method = RequestMethod.PUT)
	public String update(Menu menu) {
		menuService.save(menu);
		return "redirect:/web/menu/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Integer id) {
		menuService.delete(id);
		return "redirect:/web/menu/list";
	}
}
