package com.greenfield.springmvc.view.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.greenfield.springmvc.view.model.Book;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

// This class generates a PDF document content using iText PDF library.
public class ITextPdfView extends AbstractITextPdfView {
	
	private static final String MODEL = "bookList";
	private static final String HEADER = "Recommended books for Java";
	private static final int COLUMNS = 5;
	private static final float HUNDRED = 100f;
	private static final int[] WIDTHS = {3, 2, 2, 2, 1};
	private static final float TEN = 10f;
	private static final float FIVE = 5f;
	private static final String TITLE = "Book Title";
	private static final String AUTHOR = "Book Author";
	private static final String ISBN = "ISBN";
	private static final String DATE = "Published Date";
	private static final String PRICE = "Book Price";

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Get model data which is passed by the Spring container
		List<Book> bookList = (List<Book>) model.get(MODEL);
		
		document.add(new Paragraph(HEADER));
		
		PdfPTable table = new PdfPTable(COLUMNS);
		table.setWidthPercentage(HUNDRED);
		table.setWidths(WIDTHS);
		table.setSpacingBefore(TEN);
		
		// Define the font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		
		// Define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(FIVE);
		
		// Write table header cell
		cell.setPhrase(new Phrase(TITLE, font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase(AUTHOR, font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase(ISBN, font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase(DATE, font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase(PRICE, font));
		table.addCell(cell);
		
		// Write table row data
		for (Book book : bookList) {
			table.addCell(book.getTitle());
			table.addCell(book.getAuthor());
			table.addCell(book.getIsbn());
			table.addCell(book.getPublishedDate());
			table.addCell(book.getPrice());
		}
		
		document.add(table);
	}

}
