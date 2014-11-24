package com.jskaleel.fte.home;

import java.io.Serializable;
import java.util.ArrayList;


public class BooksHomeParser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Books books;

	public class Books {
		public ArrayList<Book> book;
		public class Book {
			public String author, category, title, bookid, pdf, epub, link, image, date;
		}
	}
}
