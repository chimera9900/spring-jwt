package com.developer.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
