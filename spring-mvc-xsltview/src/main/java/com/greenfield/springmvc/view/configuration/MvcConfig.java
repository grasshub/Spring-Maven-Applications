package com.greenfield.springmvc.view.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import com.greenfield.springmvc.view.web.HomeController;

@EnableWebMvc
@Import(HomeController.class)
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	private final String SUFFIX = ".jsp";
	private final String PREFIX = "/WEB-INF/jsp/";
	private final String SUFFIXX = ".xsl";
	private final String PREFIXX = "/WEB-INF/xsl/";
	private final int ONE = 1;
	private final int TWO = 2;
	private static final String VIEW = "XSLTView";
	private static final String XML_SOURCE = "xmlSource";
	
	@Bean
	public ViewResolver xsltViewResolver() {
		XsltViewResolver xsltViewResolver = new XsltViewResolver();
		xsltViewResolver.setOrder(ONE);
		xsltViewResolver.setSourceKey(XML_SOURCE);
		
		xsltViewResolver.setViewClass(XsltView.class);
		xsltViewResolver.setViewNames(new String[] {VIEW});
		xsltViewResolver.setPrefix(PREFIXX);
		xsltViewResolver.setSuffix(SUFFIXX);
		
		return xsltViewResolver;
	}
	
	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setOrder(TWO);

		jspViewResolver.setPrefix(PREFIX);
		jspViewResolver.setSuffix(SUFFIX);
		
		return jspViewResolver;
	}

}
