package com.sinolife.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sinolife.interceptor.PassportInterceptor;

@Component
public class AppConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	private PassportInterceptor passportInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passportInterceptor).addPathPatterns("/update*").addPathPatterns("/upload*");
		super.addInterceptors(registry);
	}
}
