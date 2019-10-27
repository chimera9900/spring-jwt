package com.developer.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.developer.security.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.userDetailsService(userDetailsService)
//		.passwordEncoder(bCryptPasswordEncoder)	
//		;
		
		auth.authenticationProvider( daoAuthenticationProvider());
		
//		auth.inMemoryAuthentication()
//		.withUser("admin").password("{noop}password")
//		.authorities("ROLE_ADMIN", "ACCESS_TEST1","ACCESS_TEST2");
	}
	
	
	@Autowired
	UserService userService;
	
	@Override@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()))
		.addFilter(new JwtAuthorizationFilter(authenticationManagerBean(), userService))
		.authorizeRequests()
//		.antMatchers("/login").permitAll()
//		.and()
//		.formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll()
//		.and()
//		.rememberMe().tokenValiditySeconds(86400).key("secret")
//		.and()
//		.logout().logoutSuccessUrl("/login")
//		.anyRequest().authenticated()
		.antMatchers("/login").permitAll()
		.antMatchers("/","/blog","/admin","/mng").permitAll()
		.antMatchers("/api/blog/**").authenticated()
		.antMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/api/mng/**").hasAuthority("ACCESS_TEST1")
//		.antMatchers("/api/**").authenticated()
		.antMatchers("/api/users").hasAuthority("ROLE_ADMIN")
		.and()
		.logout().permitAll()
		
	
		;
		
//		http.headers().frameOptions().disable();
		
	}

	@Bean
	public DaoAuthenticationProvider  daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	} 
}
















