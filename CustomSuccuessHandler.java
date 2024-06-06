package com.bookStore.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomSuccuessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		var authorities = authentication.getAuthorities();
		var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();
		
		if(roles.orElse("").equals("admin")) {
			response.sendRedirect("/home");
		}
		else if(roles.orElse("").equals("user"))
		{
			response.sendRedirect("/home1");
		}
		else
		{
			System.out.println("Error");
		}
	}

}
