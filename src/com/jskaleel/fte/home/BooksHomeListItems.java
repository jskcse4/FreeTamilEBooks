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

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
