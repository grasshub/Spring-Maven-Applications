package com.greenfield.springmvc.view.model;

public class Book {
	private String title;
	private String author;
	private String isbn;
	private String publishedDate;
	private String price;
	
	public Book(String title, String author, String isbn, String publishedDate, String price) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.publishedDate = publishedDate;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getPublishedDate() {
		return publishedDate;
	}
	
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}

}
