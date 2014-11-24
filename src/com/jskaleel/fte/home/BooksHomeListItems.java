package com.jskaleel.fte.home;

import java.io.Serializable;

public class BooksHomeListItems  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String author, category, title, pdf, epub, link, image, date;
	
	public BooksHomeListItems(String author, String category, String title,
			String pdf, String epub, String link, String image, String date) {
		// TODO Auto-generated constructor stub
		this.author = author;
		this.category = category;
		this.title = title;
		this.pdf = pdf;
		this.epub = epub;
		this.link = link;
		this.image = image;
		this.date = date;		
	}
}
