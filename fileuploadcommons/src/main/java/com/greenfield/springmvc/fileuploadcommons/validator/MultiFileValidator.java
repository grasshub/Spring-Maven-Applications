package com.greenfield.springmvc.fileuploadcommons.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.greenfield.springmvc.fileuploadcommons.model.FileBucket;
import com.greenfield.springmvc.fileuploadcommons.model.MultiFileBucket;

@Component
public class MultiFileValidator implements Validator {

	private static final int ZERO = 0;
	private static final String MISSING_FILE = "missing.file";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MultiFileBucket.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		MultiFileBucket multiBucket = (MultiFileBucket) object;
		
		int index = ZERO;
		
		for (FileBucket file : multiBucket.getFiles()) {
			if (file.getFile() != null) {
				if (file.getFile().getSize() == ZERO) {
					errors.rejectValue("files[" +index +"].file", MISSING_FILE);
				}
			}
			index++;
		}		
	}

}
