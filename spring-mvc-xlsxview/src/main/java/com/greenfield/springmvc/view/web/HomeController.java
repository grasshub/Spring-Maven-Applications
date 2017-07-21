package com.greenfield.springmvc.view.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.greenfield.springmvc.view.model.Book;

@Controller
public class HomeController {
	
	private static final String HOME = "home";
	private static final String VIEW = "excelView";
	private static final String MODEL = "bookList";
	// In order to simplify this program to focus on how to use AbstractXslxView and Apache POI library to generate .xlsx Excel file,
	// we inject the sample Model Book objects manually here.
	private static final Book[] BOOKS = {new Book("Effective Java", "Joshua Bloch", "0321356683", "May 28, 2008", "38.11"),
										 new Book("Head First Java", "Kathy Sierra & Bert Bates", "0596009208", "February 9, 2005", "30.80"),
										 new Book("Java Generics and Collections", "Philip Wadler", "0596527756", "Oct 24, 2006", "29.52"),
										 new Book("Thinking in Java", "Bruce Eckel", "0596527756", "February 20, 2006", "43.97"),
										 new Book("Spring in Action", "Craig Walls", "1935182358", "June 29, 2011", "31.98")};
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// Returns home page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showHomePage(Model model) {
		logger.info("Go to home page");
		return HOME;
	}
	
	// Handle request to download Excel document generated from Model Book object with Apache POI library
	@RequestMapping(value="/downloadExcel", method=RequestMethod.GET)
	public ModelAndView viewXSLX(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Generate the Excel file from Model");
		
		// Create sample Model Book objects here
		List<Book> bookList = new ArrayList<Book>(Arrays.asList(BOOKS));
		
		// Adds the Model objects to be used by Excel view resolver
		ModelAndView model = new ModelAndView(VIEW);
		model.addObject(MODEL, bookList);
		
		return model;
	}
	
}
