package com.greenfield.springmvc.fileuploadcommons.configuration;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
		
		private final String DISPATCHER = "dispatcher";
		private final int ONE = 1;
		// URL to access controller
		private final String PATTERN = "/";
		private static final String LOCATION = "C:/mytemp/";
		// 5M
		private static final long MAX_FILE_SIZE = 5242880;
		private static final long MAX_REQUEST_SIZE = 20971520;
		private static final int FILE_SIZE_THRESHOLD = 0; 
		
		private MultipartConfigElement getMultipartConfigElement() {
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
			return multipartConfigElement;
		}
		
		@Override
		public void onStartup(ServletContext servletContext) {
			
			// Create the dispatcher servlet application context
			AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
			dispatcherContext.register(MvcConfig.class);
			
			// Register and map the dispatcher servlet
			ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER, new DispatcherServlet(dispatcherContext));
			dispatcher.setLoadOnStartup(ONE);
			dispatcher.addMapping(PATTERN);
			// Set the MultipartConfigElement for StandardServletMultipartResolver
			dispatcher.setMultipartConfig(getMultipartConfigElement());
		}
}
