package com.greenfield.springmvc.view.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.greenfield.springmvc.view.model.Book;

// This class generates an Excel .xlsx document content using Apache POI library.
public class AbstractXLSXView extends AbstractXlsxView {
	
	private static final String MODEL = "bookList";
	private static final String SHEET_NAME = "Java Books";
	private static final int COLUMN_WIDTH = 30;
	private static final String FONT = "Arial";
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final String TITLE = "Book Title";
	private static final String AUTHOR = "Book Author";
	private static final String ISBN = "ISBN";
	private static final String DATE = "Published Date";
	private static final String PRICE = "Book Price";

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Get model data which is passed by the Spring container
		List<Book> bookList = (List<Book>) model.get(MODEL);
		
		// Create a new Excel sheet
		Sheet sheet = workbook.createSheet(SHEET_NAME);
		sheet.setDefaultColumnWidth(COLUMN_WIDTH);
		
		// Create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(FONT);
		style.setFont(font);
		
		// Create header row
		Row header = sheet.createRow(ZERO);
		
		header.createCell(ZERO).setCellValue(TITLE);
		header.getCell(ZERO).setCellStyle(style);
		
		header.createCell(ONE).setCellValue(AUTHOR);
		header.getCell(ONE).setCellStyle(style);
		
		header.createCell(TWO).setCellValue(ISBN);
		header.getCell(TWO).setCellStyle(style);
		
		header.createCell(THREE).setCellValue(DATE);
		header.getCell(THREE).setCellStyle(style);
		
		header.createCell(FOUR).setCellValue(PRICE);
		header.getCell(FOUR).setCellStyle(style);
		
		// Create data rows
		int rowCount = ONE;
		
		for (Book book : bookList) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(ZERO).setCellValue(book.getTitle());
			row.createCell(ONE).setCellValue(book.getAuthor());
			row.createCell(TWO).setCellValue(book.getIsbn());
			row.createCell(THREE).setCellValue(book.getPublishedDate());
			row.createCell(FOUR).setCellValue(book.getPrice());		
		}	
	}

}
