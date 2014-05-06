package com.tf.main;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.time.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class PdfCreator {

	public static void _main(String[] args) throws IOException,
			DocumentException {

		String outputFile = "firstdoc2.pdf";
		OutputStream os = new FileOutputStream(outputFile);

		parseHtml();

		URL oracle = new URL("http://jtidy.sourceforge.net/howto.html");
		URLConnection yc = oracle.openConnection();

		Tidy tidy = new Tidy();
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		tidy.setWraplen(Integer.MAX_VALUE);
		tidy.setXHTML(true);
		tidy.setPrintBodyOnly(true);
		tidy.setXmlOut(true);
		tidy.setSmartIndent(true);
		tidy.setXHTML(true);
		Document doc = tidy.parseDOM(yc.getInputStream(), null);

		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);
		renderer.layout();
		renderer.createPDF(os);

		os.close();
	}

	public static void main(String args[]) {
		try {
			ListLinks ll = new ListLinks("https://www.facebook.com/");
			String htmlDoc = ll.grabThePage();

			// Clean up the crappy the HTML code. This will fix a lot of
			// unclosed tag issues.
			Tidy tidy = new Tidy();
			tidy.setInputEncoding("UTF-8");
			tidy.setOutputEncoding("UTF-8");
			tidy.setWraplen(Integer.MAX_VALUE);
			tidy.setXHTML(true);
			tidy.setPrintBodyOnly(true);
			tidy.setXmlOut(true);
			tidy.setSmartIndent(true);
			tidy.setXHTML(true);

			Document doc = tidy.parseDOM(new StringReader(htmlDoc), null);
			// System.out.println("after tidy");
			// System.out.println(htmlDoc);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(doc, null);
			renderer.layout();

			// File names should be time stamps
			LocalDateTime ldt = LocalDateTime.now();
			String currentTime = Integer.toString(ldt.getHour())+ "-"
							+ Integer.toString(ldt.getMinute()) + "-"
							+ Integer.toString(ldt.getDayOfMonth()) + "-"
							+ Integer.toString(ldt.getMonthValue())+ "-"
							+ Integer.toString(ldt.getYear());

			// Output the PDF to the location
			String outputFile = currentTime+".pdf";
			OutputStream os = new FileOutputStream(outputFile);
			renderer.createPDF(os);
			os.close();
			System.out.println(outputFile);
			System.out.println("Done!");
		} catch (NullPointerException | DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseHtml() {
		// TODO Auto-generated method stub
		String url = "https://www.getdirectcredit.com/";

		try {
			org.jsoup.nodes.Document jSDoc = Jsoup.connect(url).get();
			System.out.println(jSDoc.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}