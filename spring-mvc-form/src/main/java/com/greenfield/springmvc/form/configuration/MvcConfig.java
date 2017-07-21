package com.greenfield.springmvc.form.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.greenfield.springmvc.form.exception.GlobalExceptionHandler;
import com.greenfield.springmvc.form.validator.EmailValidator;
import com.greenfield.springmvc.form.validator.UserFormValidator;
import com.greenfield.springmvc.form.web.UserController;

@EnableWebMvc
@Import({UserController.class, UserFormValidator.class, EmailValidator.class, GlobalExceptionHandler.class})
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	private final String SUFFIX = ".jsp";
	private final String PREFIX = "/WEB-INF/jsp/";
	private final String MESSAGES = "messages/messages";
	private final String VALIDATOR = "messages/validation";
	private final String RESOURCES = "/resources/**";
	private final String RES = "/resources/";
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(PREFIX);
		viewResolver.setSuffix(SUFFIX);
		
		return viewResolver;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(viewResolver());
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(new String[] {MESSAGES, VALIDATOR});
		return messageSource;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES).addResourceLocations(RES);
	}

}
