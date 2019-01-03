package model.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable {

	private static final long serialVersionUID = -63976228084869815L;
	private String bookCopyId;


	private Book book;
	private int copyNum;
	private boolean isAvailable;

	// i have change to public from protected ,, check this
	public BookCopy(Book book, int copyNum, boolean isAvailable) {
		this.bookCopyId = UUID.randomUUID().toString();
		this.book = book;
		this.copyNum = copyNum;
		this.isAvailable = isAvailable;
	}

	BookCopy(Book book, int copyNum) {
		this.book = book;
		this.copyNum = copyNum;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public int getCopyNum() {
		return copyNum;
	}

	public Book getBook() {
		return book;
	}

	public void changeAvailability() {
		isAvailable = !isAvailable;
	}

	public String getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(String bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(!(ob instanceof BookCopy)) return false;
		BookCopy copy = (BookCopy)ob;
		return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
	}

}
