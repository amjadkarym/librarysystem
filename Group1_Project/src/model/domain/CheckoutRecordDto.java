package model.domain;

import java.io.Serializable;

public class CheckoutRecordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1732112303704263142L;
	private String fullName;
	private String isbn;
	private String title;
	private String memberId;
	public CheckoutRecordDto(String fullName, String isbn, String title, String memberId) {
		this.fullName = fullName;
		this.isbn = isbn;
		this.title = title;
		this.memberId = memberId;
	}
	public CheckoutRecordDto() {
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
}
