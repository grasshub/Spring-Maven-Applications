package com.greenfield.springmvc.view.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	private static final String HOME = "home";
	private static final String RELATIVE_PATH = "resources/citizens.xml";	
	private static final String VIEW = "XSLTView";
	private static final String XML_SOURCE = "xmlSource";
	private static final String ROOT_PATH = "/";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// Returns home page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showHomePage(Model model) {
		logger.info("Go to home page");
		return HOME;
	}
	
	// Show the XSLT view for the transformation of XML data
	@RequestMapping(value="/viewXSLT", method=RequestMethod.GET)
	public ModelAndView viewXSLT(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Show XSLT View of XML Data");
		
		// Find the absolute path of web application root directory at Tomcat server deployment location
		String rootPath = request.getServletContext().getRealPath(ROOT_PATH);
		
		// Use the absolute path to access XML file 
		Source source = new StreamSource(new File(rootPath + RELATIVE_PATH));
		
		// Adds the XML source file to the model so XSLTView could detect
		ModelAndView model = new ModelAndView(VIEW);
		model.addObject(XML_SOURCE, source);
		
		return model;
	}
	
}
