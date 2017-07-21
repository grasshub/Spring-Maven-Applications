package com.greenfield.springmvc.fileuploadcommons.model;

import java.util.ArrayList;
import java.util.List;

public class MultiFileBucket {
	
	private final int SIZE = 3;
	
	List<FileBucket> files = new ArrayList<FileBucket>();
	
	public MultiFileBucket() {
		for (int index = 0; index < SIZE; index++) {
			files.add(new FileBucket());
		}
	}
	
	public List<FileBucket> getFiles() {
		return files;
	}
	
	public void setFile(List<FileBucket> files) {
		this.files = files;
	}

}
