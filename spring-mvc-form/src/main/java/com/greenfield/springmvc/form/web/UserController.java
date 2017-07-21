package com.greenfield.springmvc.form.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenfield.springmvc.form.model.User;
import com.greenfield.springmvc.form.service.UserService;
import com.greenfield.springmvc.form.validator.UserFormValidator;

@Controller
public class UserController {
	
	private static final String USERS = "users";
	private static final String USER = "user";
	private static final String USERS_USERFORM = "users/userform";
	private static final String HOME = "redirect:/users";
	private static final String SAVE_UPDATE = "redirect:/users/";
	private static final String USERS_LIST = "users/list";
	private static final String USERS_SHOW = "users/show";
	private static final String CSS = "css";
	private static final String USER_ADDED = "User added successfully";
	private static final String USER_UPDATED = "User updated successfully";
	private static final String MSG = "msg";
	private static final String USER_FORM = "userForm";
	private static final String USER_DELETED = "User is deleted";
	private static final String USER_NOT_FOUND = "User not found";
	private static final String SUCCESS = "success";
	private static final String DANGER = "danger";	
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserFormValidator userFormValidator;
	
	// Set user form validator
	@InitBinder
	public void initBinderUserFormValidator(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}
	
	// Returns home page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showHomePage(Model model) {
		logger.info("Redirect to home page");
		return HOME;
	}
	
	// List all users at home page
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String showAllUsers(Model model) {
		logger.info("Show All Users");
		model.addAttribute(USERS, userService.findAll());
		return USERS_LIST;
	}
	
	// Save or update user
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String saveOrUpdateUser(@ModelAttribute("userForm") @Valid User user, BindingResult result, Model model, 
			final RedirectAttributes redirectAttributes) {		
		logger.info("Save Or Update User : {}", user);
		
		if (result.hasErrors()) {
			populateDefaultModel(model);
			return USERS_USERFORM;
		}
		else {			
			// Add message to flash attributes
			redirectAttributes.addFlashAttribute(CSS, SUCCESS);
			if (user.isNew()) {
				redirectAttributes.addFlashAttribute(MSG, USER_ADDED);
			}
			else {
				redirectAttributes.addFlashAttribute(MSG, USER_UPDATED);
			}
			
			userService.saveOrUpdate(user);
			
			// POST/REDIRECT/GET
			return SAVE_UPDATE + user.getId();			
		}
	}
	
	// Show add user form
	@RequestMapping(value="/users/add", method=RequestMethod.GET)
	public String showAddUserForm(Model model) {		
		logger.info("Show Add User Form");
		
		User user = new User();
		
		// Set default value
		user.setName("hongyu1");
		user.setEmail("test@gmail.com");
		user.setAddress("12 Main Street");
		user.setNewsletter(true);
		user.setSex("M");
		user.setFramework(new ArrayList<String>(Arrays.asList("Spring MVC", "Struts 2")));
		user.setSkill(new ArrayList<String>(Arrays.asList("Spring", "Grails", "Groovy")));
		user.setCountry("SG");
		user.setNumber(2);
		
		model.addAttribute(USER_FORM, user);

		populateDefaultModel(model);
		
		return USERS_USERFORM;
	}

	// Show update form
	@RequestMapping(value="/users/{id}/update", method=RequestMethod.GET)
	public String showUpdateUserForm(@PathVariable("id") int id, Model model) {
		logger.info("Show Update User Form : {}", id);
		
		User user = userService.finaById(id);
		
		model.addAttribute(USER_FORM, user);

		populateDefaultModel(model);
		
		return USERS_USERFORM;
	}
	
	// Delete user
	@RequestMapping(value="/users/{id}/delete", method=RequestMethod.POST)
	public String deleteUser(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
		logger.info("Delete User : {}", id);
		
		userService.delete(id);
		
		// Add message to flash attributes
		redirectAttributes.addFlashAttribute(CSS, SUCCESS);
		redirectAttributes.addFlashAttribute(MSG, USER_DELETED);
		
		return HOME;
	}
	
	// Show user
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public String showUser(@PathVariable("id") int id, Model model) {
		logger.info("Show User : {}", id);

		User user = userService.finaById(id);
		
		if (user == null) {
			// Add message to flash attributes
			model.addAttribute(CSS, DANGER);
			model.addAttribute(MSG, USER_NOT_FOUND);
		}

		model.addAttribute(USER, user);
		return USERS_SHOW;
	}
	
	private void populateDefaultModel(Model model) {
		List<String> frameworkList = new ArrayList<String>();
		
		frameworkList.add("Spring MVC");
		frameworkList.add("Struts 2");
		frameworkList.add("JSF 2");
		frameworkList.add("GWT");
		frameworkList.add("Play");
		frameworkList.add("Apache Wicket");
		model.addAttribute("frameworkList", frameworkList);
		

		Map<String, String> skill = new HashMap<String, String>();
		skill.put("Hibernate", "Hibernate");
		skill.put("Spring", "Spring");
		skill.put("Struts", "Struts");
		skill.put("Groovy", "Groovy");
		skill.put("Grails", "Grails");
		model.addAttribute("javaSkillList", skill);

		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		model.addAttribute("numberList", numbers);

		Map<String, String> country = new HashMap<String, String>();
		country.put("US", "United Stated");
		country.put("CN", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		model.addAttribute("countryList", country);	
	}
}
