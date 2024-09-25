package com.pdf.export.examples;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import com.lowagie.text.DocumentException;
import com.phill.libs.files.PhillFileUtils;

public class Merger {

	public static void main(String[] args) throws IOException, DocumentException {

		File pdfDir = new File("D:\\Isenção\\Preliminar\\Análise Documental\\Indeferidos\\PDF");

		List<File> pdfs = PhillFileUtils.filterByExtension(pdfDir, "pdf"); Collections.sort(pdfs);

		char currentChar = 'A';

		PDFMergerUtility merger = new PDFMergerUtility();

		System.out.print("merging...");

		for (File pdf: pdfs) {

			final char firstChar = pdf.getName().charAt(0);

			if (firstChar != currentChar) {

				merger.setDestinationFileName("R:\\merged\\Nomes Iniciados em " + currentChar + ".pdf");
				merger.mergeDocuments(null);

				merger = new PDFMergerUtility();
				merger.addSource(pdf);

				currentChar = firstChar;

			}
			else
				merger.addSource(pdf);

		}

		System.out.println("done");

	}
	
}