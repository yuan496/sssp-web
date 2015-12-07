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
import com.fzb.sssp.domain.User;
import com.fzb.sssp.service.UserService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (null != id) {
			User user = userService.get(id);
			map.put("user", user);
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
		Page<User> page = userService.getPage(pageNo, 5);
		map.put("page", page);
		return "user/list";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("user", new User());
		return "user/input";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String save(User user) {
		userService.save(user);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		User user = userService.get(id);
		map.put("user", user);
		return "user/input";
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public String update(User user) {
		userService.save(user);
		return "redirect:/web/user/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Integer id) {
		userService.delete(id);
		return "redirect:/web/user/list";
	}
}
