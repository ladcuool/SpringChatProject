package com.project.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	public AuthenticationSuccessHandler authenticationSuccessHandler;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/css/**","/js/**","/img/**").permitAll()
				.antMatchers("/loginForm","/join").permitAll()
				.antMatchers("/list","/list2").hasAnyRole("USER","MEMBER","ADMIN")
				.antMatchers("/manager_write_view","/manager_modify_view").hasRole("ADMIN")
				.anyRequest().permitAll();
		
		http.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/j_spring_security_check")
				.failureHandler(authenticationFailureHandler)
				.usernameParameter("userID")
				.passwordParameter("userPassword")
				.successHandler(authenticationSuccessHandler)
				.permitAll();
		
		http.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.permitAll();
		
		//ssl을 사용하지 않으면 true로 사용
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
	}
	
	@Autowired
	private DataSource dataSource;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select userID as userName, userPassword as password, enabled"
									+" from userInfo where userID=?")
			.authoritiesByUsernameQuery("select userID as userName, authority "
										+" from userInfo where userID=?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
		
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
