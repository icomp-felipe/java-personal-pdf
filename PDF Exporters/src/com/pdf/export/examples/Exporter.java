package com.pdf.export.examples;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jodconverter.core.office.OfficeException;

import com.pdf.export.LibreOfficePDFExporter;
import com.phill.libs.files.PhillFileUtils;

public class Exporter {

	public static void main(String[] args) throws IOException, OfficeException {
		
		File inputDir  = new File("D:\\Felipe's Files\\Google Drive\\COMPEC\\08. PSC 2025 - Etapa 3\\Isenção\\01. Preliminar\\Análise Documental\\Indeferidos");
		File outputDir = new File("R:\\pdf"); outputDir.mkdirs();

		List<File> filtered = PhillFileUtils.filterByExtension(inputDir, "docx"); Collections.sort(filtered);

		// Preparando filtros de exportação pra PDF
    	final Map<String, Object> filterData = new HashMap<>();
    	filterData.put("ExportFormFields", false);

    	final Map<String, Object> customProperties = new HashMap<>();
    	customProperties.put("Overwrite", true);
    	customProperties.put("FilterData", filterData);

		LibreOfficePDFExporter.toPDF(filtered, outputDir, customProperties, true);
		
	}
	
}