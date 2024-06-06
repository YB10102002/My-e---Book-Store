package com.bookStore.configurations;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bookStore.service.CustomSuccuessHandler;
import com.bookStore.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecutiryConfig {
	
	@Autowired
	CustomSuccuessHandler customSuccuessHandler;
	@Autowired
	CustomUserDetailService customUserDetailService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(c -> c.disable())
		
		.authorizeHttpRequests(request -> request.regexMatchers("/home").hasAuthority("admin").regexMatchers("/home1").permitAll().regexMatchers("/register").permitAll()
				.anyRequest().authenticated())
		
		.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").successHandler(customSuccuessHandler).permitAll())
		
		.logout(form -> form.invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout").permitAll());
		return http.build();
	}
	
	@Autowired
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	
    
}
