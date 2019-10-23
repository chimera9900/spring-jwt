package com.developer.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder)	
		;
//		auth.inMemoryAuthentication()
//		.withUser("admin").password("{noop}password")
//		.authorities("ROLE_ADMIN", "ACCESS_TEST1","ACCESS_TEST2");
		
	
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/blog/**").authenticated()
		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/mng/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
		.antMatchers("/api/**").authenticated()
		.antMatchers("/users").hasAuthority("ROLE_ADMIN")
		.and()
		.httpBasic()
		;
		
		http.headers().frameOptions().disable();
		http.csrf().disable();
	}

}
