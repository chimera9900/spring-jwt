package com.developer.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.developer.security.model.User;
import com.developer.security.utils.UserRowMapper;

@Service
public class UserService {
	
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public List<User> fetchAll(){
		
		String sql = "select * from user";
		List<User> users = jdbc.query(sql, new UserRowMapper());
		return users;
		
	}
	
	public User fetchByUsername(String username) {
		String sql = "select * from user u where u.username = :username";
		User user = jdbc.queryForObject(sql, new MapSqlParameterSource("username", username), new UserRowMapper());
		return user;
	}

}
