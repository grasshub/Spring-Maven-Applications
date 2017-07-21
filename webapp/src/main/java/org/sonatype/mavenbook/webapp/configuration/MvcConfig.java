package org.sonatype.mavenbook.webapp.configuration;

import org.sonatype.mavenbook.webapp.HistoryController;
import org.sonatype.mavenbook.webapp.WeatherController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

// Need to import the two controllers at different package so dispatcher servlet could find the RequestMapping at controllers
@SuppressWarnings("deprecation")
@EnableWebMvc
@Import({WeatherController.class, HistoryController.class})
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	private final String PREFIX = "";
	private final String SUFFIX = ".vm";
	private final boolean TRUE = true;
	private final String PATH = "/WEB-INF/velocity/";
	
	@Bean
	public VelocityConfigurer velocityConfigurer() {
		
		VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
		velocityConfigurer.setResourceLoaderPath(PATH);
		
		return velocityConfigurer;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		
		VelocityViewResolver resolver = new VelocityViewResolver();
        resolver.setPrefix(PREFIX);
        resolver.setSuffix(SUFFIX);
        resolver.setCache(TRUE);
        
        return resolver;
    }

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
       
        registry.viewResolver(viewResolver());
    }
		
}
