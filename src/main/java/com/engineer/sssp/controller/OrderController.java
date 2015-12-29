package com.engineer.sssp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.engineer.sssp.entity.Order;
import com.engineer.sssp.service.MenuService;
import com.engineer.sssp.service.OrderService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MenuService menuService;
	
	@ModelAttribute
	public void getOrder(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
		if (null != id) {
			Order order = orderService.get(id);
			order.setOwners(null);
			map.put("order", order);
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
		Page<Order> page = orderService.getPage(pageNo, 5);
		map.put("page", page);
		return "order/list";
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("menus", menuService.getAll());
		map.put("order", new Order());
		return "order/input";
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String save(Order order) {
		orderService.save(order);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map) {
		Order order = orderService.get(id);
		map.put("order", order);
		map.put("menus", menuService.getAll());
		return "order/input";
	}
	
	@RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
	public String update(Order order) {
		orderService.save(order);
		return "redirect:/order/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Long id) {
		orderService.delete(id);
		return "redirect:/order/list";
	}
}
