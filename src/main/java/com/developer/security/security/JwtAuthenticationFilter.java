package com.developer.security.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.developer.security.model.UsernamePassword;
import com.developer.security.utils.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		UsernamePassword login = null;
		
		try {
			login = new ObjectMapper().readValue(request.getInputStream(), UsernamePassword.class);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				login.getUsername(), login.getPassword(), new ArrayList<>());
		
		Authentication auth = authenticationManager.authenticate(token);
		return auth;
//		String username = obtainUsername(request);
//		String password = obtainPassword(request);
		
//		return getAuthenticationManager().authenticate(token);
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ((UserDetails)authResult.getPrincipal()).getUsername();
		
		String token = JWT.create()
		.withSubject(username)
		.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
		.sign(Algorithm.HMAC384(JwtProperties.SECRET.getBytes()));
		
		response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX + token);
		
		
	}
}
