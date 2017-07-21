package com.greenfield.springmvc.view.configuration;

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
	
	@Override
	public void onStartup(ServletContext servletContext) {
		
		// Create the dispatcher servlet application context
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(MvcConfig.class);
		
		// Register and map the dispatcher servlet
		DispatcherServlet dispatherServlet = new DispatcherServlet(dispatcherContext);
		// Set the throw exception if no handler found so you could define exception handler to map the specific exception to error pages.
		//dispatherServlet.setThrowExceptionIfNoHandlerFound(true);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER, dispatherServlet);
		dispatcher.setLoadOnStartup(ONE);
		dispatcher.addMapping(PATTERN);
	}
	
}
