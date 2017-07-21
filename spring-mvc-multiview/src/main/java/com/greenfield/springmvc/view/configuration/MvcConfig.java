package com.greenfield.springmvc.view.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.greenfield.springmvc.view.model.BookList;
import com.greenfield.springmvc.view.view.AbstractXLSXView;
import com.greenfield.springmvc.view.view.ITextPdfView;
import com.greenfield.springmvc.view.web.HomeController;

@EnableWebMvc
@Import(HomeController.class)
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	private final String SUFFIX = ".jsp";
	private final String PREFIX = "/WEB-INF/jsp/";
	private final int ONE = 1;
	private final int TWO = 2;
	private static final String XML = "xml";
	private static final String JSON = "json";
	private static final String PDF = "pdf";
	private static final String XLSX = "xlsx";
	private static final String APPLICATION = "application";
	private static final String XLSX_TYPE = "vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final boolean TRUE = true;
	private final String RESOURCES = "/resources/**";
	private final String RES = "/resources/";
	
	// Add the MediaType for XML, JSON, PDF and XLSX
	private static Map<String, MediaType> MEDIA_TYPES = createMediaTypes();
	private static Map<String, MediaType> createMediaTypes() {
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put(XML, MediaType.APPLICATION_XML);
		mediaTypes.put(JSON, MediaType.APPLICATION_JSON);
		mediaTypes.put(PDF, MediaType.APPLICATION_PDF);
		mediaTypes.put(XLSX, new MediaType(APPLICATION, XLSX_TYPE));
		
		return mediaTypes;
	}
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setOrder(ONE);
		
		List<View> defaultViews = new ArrayList<View>();
		MarshallingView marshallingView = new MarshallingView();
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		// Associate with model object at JAXB
		jaxb2Marshaller.setClassesToBeBound(BookList.class);
		marshallingView.setMarshaller(jaxb2Marshaller);
		// Add the ViewResolve for XML
		defaultViews.add(marshallingView);
		// Add the ViewResolve for JSON
		defaultViews.add(new MappingJackson2JsonView());
		// Add the ViewResolve for PDF
		defaultViews.add(new ITextPdfView());
		// Add the ViewResolve for Excel
		defaultViews.add(new AbstractXLSXView());
		
		contentNegotiatingViewResolver.setDefaultViews(defaultViews);
		
		return contentNegotiatingViewResolver;
	}
	
	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setOrder(TWO);

		jspViewResolver.setPrefix(PREFIX);
		jspViewResolver.setSuffix(SUFFIX);
		
		return jspViewResolver;
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(TRUE).mediaTypes(MEDIA_TYPES);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES).addResourceLocations(RES);
	}

}
