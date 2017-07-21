package com.greenfield.springmvc.form.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {
	public static final String DEFAULT_ERROR_VIEW = "error";
	private static final String EXCEPTION = "exception";
	private static final String URL = "url";

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		
		logger.error("[URL] : {}", req.getRequestURL(), e);
		
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// Otherwise setup and send the user to a default error-view.
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(EXCEPTION, e);
		modelAndView.addObject(URL, req.getRequestURL());
		modelAndView.setViewName(DEFAULT_ERROR_VIEW);
		return modelAndView;
	}
	
}