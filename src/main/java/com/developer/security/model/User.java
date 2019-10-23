package com.developer.security.model;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private int id;
	private String username;
	private String password;
	private int active;
	private String roles;
	private String permission;
	
	public List<String> getRoleList(){
		return Arrays.asList(roles.split(","));
	}
	
	public List<String> getPermissionList(){
		return Arrays.asList(permission.split(","));
	}
	
	

}


