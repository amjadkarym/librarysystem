package model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class CheckoutRecordEntry implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4779723093305079342L;
	private String entryId;
	private Date checkoutDate;
	private Date dueDate;
	private BookCopy bookCopy;
	public CheckoutRecordEntry(BookCopy bookCopy, Date checkoutDate, Date dueDate) {
		this.entryId = UUID.randomUUID().toString();
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
	}
	public String getEntryId() {
		return entryId;
	}
	public BookCopy getBookCopy() {
		return bookCopy;
	}
	public Date getDueDate() {
		return dueDate;
	}




}
