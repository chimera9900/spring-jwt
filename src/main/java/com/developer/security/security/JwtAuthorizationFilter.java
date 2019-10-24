package com.developer.security.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.developer.security.model.User;
import com.developer.security.service.UserPrincipalDetails;
import com.developer.security.service.UserService;
import com.developer.security.utils.JwtProperties;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private UserService userService;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
			UserService userService) {
		super(authenticationManager);
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX) ) {
		      chain.doFilter(request, response);
		      return;
		}
		
		Authentication authentication = getUsernamePasswordToken(header);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
		
	}

	private Authentication getUsernamePasswordToken(String header) {
		String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
		if(token != null) {
			String username = JWT.require(Algorithm.HMAC384(JwtProperties.SECRET.getBytes()))
					.build()
					.verify(token).getSubject();
			if(username != null) {
				
				User user = userService.fetchByUsername(username);
				UserDetails userPrincipalDetails = new UserPrincipalDetails(user);
				
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipalDetails.getUsername(), null, userPrincipalDetails.getAuthorities());
				
				return auth;
			}
			return null;
		}
		return null;
	}

}
