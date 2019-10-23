package com.developer.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developer.security.model.User;
import com.developer.security.service.UserService;

@Controller
@RequestMapping
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET,value = "/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/blog/index")
	public @ResponseBody String blog() {
		return "authenticated";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/admin/index")
	public @ResponseBody String admin() {
		return "admin authenticated";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/mng/index")
	public @ResponseBody String mng() {
		return "management authenticated";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/api/test1")
	public @ResponseBody String test1() {
		return "public api test1";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/api/test2")
	public @ResponseBody String test2() {
		return "public api test2";
	}
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, value="/users")
	public @ResponseBody List<User> fetchAll() {
		return userService.fetchAll();
	}
	
	

}
