package com.greenfield.springmvc.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.greenfield.springmvc.form.model.User;

@Component
public class UserFormValidator implements Validator {
	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}
	
	public void validatePage1UserForm(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "NotEmpty.userForm.sex");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.userForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.userForm.country");
		
		if (user.getCountry().equalsIgnoreCase("none")) {
			errors.rejectValue("country", "NotEmpty.userForm.country");
		}
		
		if (!emailValidator.valid(user.getEmail())) {
			errors.rejectValue("email", "Pattern.userForm.email");
		}
	}
	
	public void validatePage2UserForm(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword","NotEmpty.userForm.confirmPassword");
		
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Diff.userform.confirmPassword");
		}
	}
	
	public void validatePage3UserForm(Object target, Errors errors) {
		User user = (User) target;
		
		if (user.getNumber() == null || user.getNumber() <= 0) {
			errors.rejectValue("number", "NotEmpty.userForm.number");
		}

		if (user.getFramework() == null || user.getFramework().size() < 2) {
			errors.rejectValue("framework", "Valid.userForm.framework");
		}

		if (user.getSkill() == null || user.getSkill().size() < 3) {
			errors.rejectValue("skill", "Valid.userForm.skill");
		}
	}

	@Override
	public void validate(Object target, Errors errors) {	
		validatePage1UserForm(target, errors);
		validatePage2UserForm(target, errors);
		validatePage3UserForm(target, errors);
	}
	
}
