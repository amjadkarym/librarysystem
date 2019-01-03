package model.domain;

import java.io.Serializable;

public class CheckoutOverdueDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5250919663763867656L;
	private String isbn;
	private String title;
	private String bookCopyNo;
	private String memberName;
	private String dueDate;


	public CheckoutOverdueDto() {
	}
	public CheckoutOverdueDto(String isbn, String title, String bookCopyNo, String memberName, String dueDate) {
		this.isbn = isbn;
		this.title = title;
		this.bookCopyNo = bookCopyNo;
		this.memberName = memberName;
		this.dueDate = dueDate;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBookCopyNo() {
		return bookCopyNo;
	}
	public void setBookCopyNo(String bookCopyNo) {
		this.bookCopyNo = bookCopyNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


}
