package com.greenfield.springmvc.fileuploadcommons.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.greenfield.springmvc.fileuploadcommons.model.FileBucket;

@Component
public class FileValidator implements Validator {
	
	private static final int ZERO = 0;
	private static final String FILE = "file";
	private static final String MISSING_FILE = "missing.file";

	@Override
	public boolean supports(Class<?> clazz) {	
		return FileBucket.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		FileBucket file = (FileBucket) object;
		
		if (file.getFile() != null) {
			if (file.getFile().getSize() == ZERO) {
				errors.rejectValue(FILE, MISSING_FILE);
			}
		}		
	}

}
