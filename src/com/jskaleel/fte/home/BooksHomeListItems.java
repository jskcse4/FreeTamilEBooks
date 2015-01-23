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
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the pdf
	 */
	public String getPdf() {
		return pdf;
	}

	/**
	 * @param pdf the pdf to set
	 */
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	/**
	 * @return the epub
	 */
	public String getEpub() {
		return epub;
	}

	/**
	 * @param epub the epub to set
	 */
	public void setEpub(String epub) {
		this.epub = epub;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the image
	 */
	public String getBookImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setBookImage(String image) {
		this.image = image;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
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
