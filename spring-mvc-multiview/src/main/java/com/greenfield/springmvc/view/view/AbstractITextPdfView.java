package com.greenfield.springmvc.view.view;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractITextPdfView extends AbstractView {
	
	private static final String CONTENT_TYPE = "application/pdf";
	
	public AbstractITextPdfView() {
		setContentType(CONTENT_TYPE);
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	// Leave this method to be empty for now, allow subclasses to override this method to build PDF metadata later.
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {}
	
	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, 
											 HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// Subclass must implement this method to actually render the view
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream outputStream = createTemporaryOutputStream();
		
		// Apply preferences and build metadata
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
		buildPdfMetadata(model, document, request);
		
		document.open();
		// Leave this method as abstract method so the subclass could use to add PDF document content based on model data.
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		writeToResponse(response, outputStream);
	}
	
}
