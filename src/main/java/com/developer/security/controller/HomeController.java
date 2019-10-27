package com.developer.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.developer.security.model.User;
import com.developer.security.service.UserService;
import com.developer.security.utils.JwtProperties;

@Controller
@RequestMapping
@CrossOrigin
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET,value = "/")
	public String index() {
		return "index";
	}
	
	@Autowired
	RestTemplate rest;
	
	@RequestMapping(method = RequestMethod.GET,value = "/blog")
	public @ResponseBody String blog() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + JwtProperties.token );
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = rest.exchange("http://localhost:8080/api/blog/index", HttpMethod.GET, 
				entity, String.class);
		
		return responseEntity.getBody().toString();
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/admin")
	public  @ResponseBody  String admin() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + JwtProperties.token );
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = rest.exchange("http://localhost:8080/api/admin/index", HttpMethod.GET, 
				entity, String.class);
		
		return responseEntity.getBody().toString();
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/mng")
	public  @ResponseBody  String mng() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + JwtProperties.token );
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseEntity = rest.exchange("http://localhost:8080/api/mng/index", HttpMethod.GET, 
				entity, String.class);
		
		return responseEntity.getBody().toString();
	
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/api/blog/index")
	public @ResponseBody String blogdb() {
		return "authenticated";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/api/admin/index")
	public @ResponseBody String admindb() {
		return "admin authenticated";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/api/mng/index")
	public @ResponseBody String mngdb() {
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
