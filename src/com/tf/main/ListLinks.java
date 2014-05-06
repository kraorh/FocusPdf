package com.tf.main;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
	String strUrl;

	public ListLinks(String url) {
		this.strUrl = url;
	}

	public String grabThePage() {
		System.out.println("Fetching from " + strUrl);
		Document doc = null;
		try {
			doc = Jsoup.connect(strUrl).get();
			resolveAllCrappyLinks(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc.toString();
	}

	private static void resolveAllCrappyLinks(Document doc) {
		String linksToResolve[] = { "src", "href", "background" };
		for (int i = 0; i < linksToResolve.length; i++) {
			Elements eles = doc.getElementsByAttribute(linksToResolve[i]);
			for (Element e : eles) {
				e.attr(linksToResolve[i], e.absUrl(linksToResolve[i]));
				System.out.println(e);
			}
		}
	}

}
