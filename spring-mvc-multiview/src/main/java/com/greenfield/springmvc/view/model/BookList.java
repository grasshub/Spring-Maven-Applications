package com.greenfield.springmvc.view.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookList {
	
	private List<Book> bookList;
	
	public BookList() {}
	
	public BookList(List<Book> bookList) {
	     this.bookList = bookList;
	}
	
	@XmlElement
	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
}
