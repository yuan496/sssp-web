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
import com.fzb.sssp.domain.MobileUser;
import com.fzb.sssp.service.MobileUserService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 下午7:16:49
 */
@Controller
@RequestMapping("/mobileUser")
public class MobileUserController {
	
	@Autowired
	private MobileUserService mobileUserService;
	
	@ModelAttribute
	public void getMobileUser(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
		if (null != id) {
			MobileUser mobileUser = mobileUserService.get(id);
			map.put("mobileUser", mobileUser);
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
		Page<MobileUser> page = mobileUserService.getPage(pageNo, 5);
		map.put("page", page);
		return "mobileUser/list";
	}
	
	@RequestMapping(value = "/mobileUser", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("mobileUser", new MobileUser());
		return "mobileUser/input";
	}
	
	@RequestMapping(value = "/mobileUser", method = RequestMethod.POST)
	public String save(MobileUser mobileUser) {
		mobileUserService.save(mobileUser);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/mobileUser/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map) {
		MobileUser mobileUser = mobileUserService.get(id);
		map.put("mobileUser", mobileUser);
		return "mobileUser/input";
	}
	
	@RequestMapping(value = "/mobileUser/{id}", method = RequestMethod.PUT)
	public String update(MobileUser mobileUser) {
		mobileUserService.save(mobileUser);
		return "redirect:/mobileUser/list";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id") Long id) {
		mobileUserService.delete(id);
		return "redirect:/mobileUser/list";
	}
}
