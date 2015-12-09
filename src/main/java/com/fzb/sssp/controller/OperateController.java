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
import com.fzb.sssp.domain.Operate;
import com.fzb.sssp.service.MenuService;
import com.fzb.sssp.service.OperateService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/operate")
public class OperateController {
	
	@Autowired
	private OperateService operateService;
	
	@Autowired
	private MenuService menuService;
	
	@ModelAttribute
	public void getOperate(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
		if (null != id) {
			Operate operate = operateService.get(id);
			operate.setMenu(null);
			map.put("operate", operate);
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
		Page<Operate> page = operateService.getPage(pageNo, 5);
		map.put("page", page);
		return "operate/list";
	}
	
	@RequestMapping(value = "/operate", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("menus", menuService.getAll());
		map.put("operate", new Operate());
		return "operate/input";
	}
	
	@RequestMapping(value = "/operate", method = RequestMethod.POST)
	public String save(Operate operate) {
		operateService.save(operate);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/operate/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map) {
		Operate operate = operateService.get(id);
		map.put("operate", operate);
		map.put("menus", menuService.getAll());
		return "operate/input";
	}
	
	@RequestMapping(value = "/operate/{id}", method = RequestMethod.PUT)
	public String update(Operate operate) {
		operateService.save(operate);
		return "redirect:/operate/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Long id) {
		operateService.delete(id);
		return "redirect:/operate/list";
	}
}
