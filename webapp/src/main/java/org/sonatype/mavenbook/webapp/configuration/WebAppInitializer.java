package org.sonatype.mavenbook.webapp.configuration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.sonatype.mavenbook.weather.configuration.WeatherJavaConfig;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
	
	private final String DISPATCHER = "dispatcher";
	private final int ONE = 1;
	// Extension to access controllers
	private final String PATTERN = "*.x";
	private final String SESSION_FACTORY = "sectionFactory";
	private final String FILTER_NAME = "openSessionInView";
	private final boolean TRUE = true;
	
	@Override
	public void onStartup(ServletContext servletContext) {
		
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WeatherJavaConfig.class);
		
		// Manage the life cycle of the servlet context
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		// Create the dispatcher servlet application context
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(MvcConfig.class);
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER, new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(ONE);
		dispatcher.addMapping(PATTERN);
		
		// Need to add the OpenSessionInViewFilter to servletContext to make session available till the request fully processed
		// by view so the association list lazily initialized are loaded through the same session even the original transaction was already completed.
		// From the various DAOImpl classes after retrieving entities with lazily loaded attributes, Hibernate.initizlize method already being used
		// to manually load these collection attributes, so open session in view could be removed here. Just keep here as reference sample implementation.
		OpenSessionInViewFilter sessionFilter = new OpenSessionInViewFilter();
		sessionFilter.setSessionFactoryBeanName(SESSION_FACTORY);
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
		FilterRegistration.Dynamic openSessionInView = servletContext.addFilter(FILTER_NAME, sessionFilter);
		openSessionInView.addMappingForUrlPatterns(dispatcherTypes, TRUE, PATTERN);
	}
	
}
