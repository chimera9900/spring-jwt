package com.developer.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.developer.security.model.User;

public class UserPrincipalDetails implements UserDetails {
	
	private static final long serialVersionUID = -5516887095018649945L;
	private User user;
	

	public UserPrincipalDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		user.getPermissionList().forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
		user.getRoleList().forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getActive()==1;
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

}
