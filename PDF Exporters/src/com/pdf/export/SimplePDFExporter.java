package com.pdf.export;

import java.io.*;

import org.apache.poi.xwpf.usermodel.*;

import fr.opensagres.poi.xwpf.converter.pdf.*;

/** Lightweight class to convert .docx files to PDF format using Apache POI.
 *  @author Felipe André - felipeandre.eng@gmail.com
 *  @version 1.0, 27/MAR/2023 */
public class SimplePDFExporter {
	
	/** Converts a .docx file to PDF.
	 *  @param source - souce .docx file
	 *  @param target - target PDF file
	 *  @throws IOException when source or target cannot be manipulated for some reason or source file has other extension than .docx */
    public static void toPDF(final File source, final File target) throws IOException {
    	
    	if (!source.getName().toLowerCase().endsWith(".docx"))
    		throw new IOException("Source file must be in .docx format!");
    	
    	final FileInputStream  inputStream  = new FileInputStream (source);
    	final FileOutputStream outputStream = new FileOutputStream(target);
    	
    	XWPFDocument document = new XWPFDocument(inputStream);
    	PdfOptions pdfOptions = PdfOptions.create();

    	PdfConverter.getInstance().convert(document, outputStream, pdfOptions);
    	
    }
    
}