package com.greenfield.springmvc.fileuploadcommons.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.greenfield.springmvc.fileuploadcommons.controller.FileUploadController;
import com.greenfield.springmvc.fileuploadcommons.validator.FileValidator;
import com.greenfield.springmvc.fileuploadcommons.validator.MultiFileValidator;

@EnableWebMvc
@Import({FileUploadController.class, FileValidator.class, MultiFileValidator.class})
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	//private static final int MAXSIZE = 5242880;
	private final String SUFFIX = ".jsp";
	private final String PREFIX = "/WEB-INF/views/";
	private final String MESSAGES = "messages";
	private final String STATIC = "/static/**";
	private final String CSS = "/static/";
	
	/*
	@Bean 
	public CommonsMultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		
		// Set the maximum allowed size (in bytes) for each individual file.
		resolver.setMaxUploadSizePerFile(MAXSIZE);
		
		return resolver;
	}
	*/
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(PREFIX);
		viewResolver.setSuffix(SUFFIX);
		
		registry.viewResolver(viewResolver);
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGES);
		return messageSource;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(STATIC).addResourceLocations(CSS);
	}

}
