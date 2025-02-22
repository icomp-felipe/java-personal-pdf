package com.pdf.export;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

import org.jodconverter.local.*;
import org.jodconverter.local.office.*;

import org.jodconverter.core.document.*;
import org.jodconverter.core.office.*;

import com.pdf.export.concurrent.*;

/** Most reliable class to convert files to PDF format, using a pre-installed LibreOffice.
 *  @author Felipe André - felipeandre.eng@gmail.com
 *  @version 1.0, 27/MAR/2023 */
public class LibreOfficePDFExporter {

    /** Converts a stream of bytes to PDF format.
     *  @param source - source (array of bytes)
     *  @param format - format of the generated array of bytes
     *  @param target - target PDF file
     *  @param options - map of export options
     *  @throws IOException when the target file cannot be written
     *  @throws OfficeException when the conversion fails
     *  @see DefaultDocumentFormatRegistry
     *  @see <a href="https://github.com/jodconverter/jodconverter/issues/160">JOD Converter Map Specification for options</a>
     *  @see <a href="https://forum.openoffice.org/en/forum/viewtopic.php?f=44&t=1804&start=0">OpenOffice format specification</a> */
    public static void toPDF(final byte[] source, final DocumentFormat format, final File target, final Map<String, Object> options) throws IOException, OfficeException {
    	
    	final ByteArrayInputStream inputStream = new ByteArrayInputStream(source);
    	final LocalOfficeManager officeManager = LocalOfficeManager.install(); officeManager.start();
    	
    	LocalConverter.builder()
        	.storeProperties(options)
        	.officeManager(officeManager)
        	.build()
        	.convert(inputStream, true)
        	.as(format)
        	.to(target)
        	.as(DefaultDocumentFormatRegistry.PDF)
        	.execute();
    	
    	officeManager.stop();
    	
    }
    
    /** Converts a source file do PDF format.<br>
     *  <b>Note:</b> the format of the source is defined by its file extension!
     *  @param source - source file
     *  @param target - target PDF file
     *  @param options - map of export options
     *  @throws IOException when the source or target cannot be manipulated
     *  @throws OfficeException when the conversion fails
     *  @see DefaultDocumentFormatRegistry
     *  @see <a href="https://github.com/jodconverter/jodconverter/issues/160">JOD Converter Map Specification for options</a>
     *  @see <a href="https://forum.openoffice.org/en/forum/viewtopic.php?f=44&t=1804&start=0">OpenOffice format specification</a> */
    public static void toPDF(final File source, final File target, final Map<String, Object> options) throws IOException, OfficeException {
    	
    	final String extension = FilenameUtils.getExtension(source.getName());
    	final LocalOfficeManager officeManager = LocalOfficeManager.install(); officeManager.start();
    	
    	LocalConverter.builder()
        	.storeProperties(options)
        	.officeManager(officeManager)
        	.build()
        	.convert(source)
        	.as(DefaultDocumentFormatRegistry.getFormatByExtension(extension))
        	.to(target)
        	.as(DefaultDocumentFormatRegistry.PDF)
        	.execute();
    	
    	officeManager.stop();
    	
    }
    
    public static void runPDF(final File source, final File target, final Map<String, Object> options, final LocalOfficeManager officeManager) throws IOException, OfficeException {
    	
    	final String extension = FilenameUtils.getExtension(source.getName());
    	
    	System.out.println("Generating " + target.getName()); System.out.flush();
    	
    	LocalConverter.builder()
        	.storeProperties(options)
        	.officeManager(officeManager)
        	.build()
        	.convert(source)
        	.as(DefaultDocumentFormatRegistry.getFormatByExtension(extension))
        	.to(target)
        	.as(DefaultDocumentFormatRegistry.PDF)
        	.execute();
    	
    	
    }
    
    public static void toPDF(final List<File> sourceFiles, final File targetDir, final Map<String, Object> options, final boolean turboMode) throws IOException, OfficeException {
    	
    	final int threads = turboMode ? Runtime.getRuntime().availableProcessors() : 1;
    	
    	final ThreadPool threadPool = new ThreadPool(threads);
    	
    	final LocalOfficeManager officeManager = LocalOfficeManager.install(); officeManager.start();
    	
    	for (File source: sourceFiles) {
    		
    		String targetName = FilenameUtils.removeExtension(source.getName()) + ".pdf";
    		File target = new File(targetDir, targetName);
    		
    		Runnable job = () -> { try { runPDF(source, target, options, officeManager); } catch (Exception exception) { exception.printStackTrace(); } };
    		
    		threadPool.execute(job);
    		
    	}
    	
    }

}