package com.greenfield.springmvc.fileuploadcommons.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.greenfield.springmvc.fileuploadcommons.model.FileBucket;
import com.greenfield.springmvc.fileuploadcommons.model.MultiFileBucket;
import com.greenfield.springmvc.fileuploadcommons.validator.FileValidator;
import com.greenfield.springmvc.fileuploadcommons.validator.MultiFileValidator;

@Controller
public class FileUploadController {
	
	private static final String UPLOAD_LOCATION = "C:/mytemp/";
	private static final String FILE_BUCKET = "fileBucket";
	private static final String MULTIFILE_BUCKET = "multiFileBucket";
	private static final String WELCOME = "welcome";
	private static final String SINGLE_FILE_UPLOADER = "singleFileUploader";
	private static final String MULTI_FILE_UPLOADER = "multiFileUploader";
	private static final String SINGLE_UPLOAD_ERROR = "Validation errors for singleFileUpload";
	private static final String MULTI_UPLOAD_ERROR = "Validation errors for multiFileUpload";
	private static final String FETCHING_FILE = "Fetching file";
	private static final String FETCHING_FILES = "Fetching files";
	private static final String FILE_NAME = "fileName";
	private static final String FILE_NAMES = "fileNames";
	private static final String SUCCESS = "success";
	private static final String MULTI_SUCCESS = "multiSuccess";
	
	@Autowired
	FileValidator fileValidator;
	
	@Autowired
	MultiFileValidator multiFileValidator;
	
	@InitBinder("fileBucket")
	public void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}
	
	@InitBinder("multiFileBucket")
	public void initBinderMultiFileBucket(WebDataBinder binder) {
		binder.setValidator(multiFileValidator);
	}
	
	// Returns welcome page
	@RequestMapping(value={"/", "/welcome"}, method=RequestMethod.GET)
	public String getHomePage(ModelMap model) {
		return WELCOME;
	}
	
	@RequestMapping(value="/singleUpload", method=RequestMethod.GET)
	public String getSingleUploadPage(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute(FILE_BUCKET, fileModel);
		return SINGLE_FILE_UPLOADER;
	}
	
	@RequestMapping(value="/singleUpload", method=RequestMethod.POST)
	public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
		
		if (result.hasErrors()) {
			System.out.println(SINGLE_UPLOAD_ERROR);
			return SINGLE_FILE_UPLOADER;
		}
		else {
			System.out.println(FETCHING_FILE);
			MultipartFile multipartFile = fileBucket.getFile();
			
			// Now save the file
			multipartFile.transferTo(new File(UPLOAD_LOCATION + multipartFile.getOriginalFilename()));
			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute(FILE_NAME, fileName);
			return SUCCESS;
		}
	}
	
	@RequestMapping(value="/multiUpload", method=RequestMethod.GET)
	public String getMultiUploadPage(ModelMap model) {
		MultiFileBucket fileModel = new MultiFileBucket();
		model.addAttribute(MULTIFILE_BUCKET, fileModel);
		return MULTI_FILE_UPLOADER;
	}

	@RequestMapping(value="/multiUpload", method=RequestMethod.POST)
	public String multiFileUpload(@Valid MultiFileBucket multiFileBucket, BindingResult result, ModelMap model) throws IOException {
		
		if (result.hasErrors()) {
			System.out.println(MULTI_UPLOAD_ERROR);
			return MULTI_FILE_UPLOADER;
		}
		else {
			System.out.println(FETCHING_FILES);
			List<String> fileNames = new ArrayList<String>();
			
			// Now save the files
			for (FileBucket fileBucket : multiFileBucket.getFiles()) {
				fileBucket.getFile().transferTo(new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
				fileNames.add(fileBucket.getFile().getOriginalFilename());
			}
			
			model.addAttribute(FILE_NAMES, fileNames);
			return MULTI_SUCCESS;
		}
	}
	
}
