package model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class CheckoutRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3746241834812532762L;
	private String recordId;
	/*private String memberId;
	private String entryId;*/

	private LibraryMember libraryMember;
	private CheckoutRecordEntry checkoutRecordEntry;



	public CheckoutRecord(LibraryMember libraryMember, CheckoutRecordEntry checkoutRecordEntry) {
		this.libraryMember = libraryMember;
		this.checkoutRecordEntry = checkoutRecordEntry;
	}

	/*public CheckoutRecord(String memberId, String entryId) {
		this.recordId = UUID.randomUUID().toString();
		this.memberId = memberId;
		this.entryId = entryId;
	}*/

	private List<CheckoutRecordEntry> checkoutEntries;
	// my implement private CheckoutManager checkoutController;

	public List<CheckoutRecordEntry> getMemberCheckoutRecord(LibraryMember member) {
	// my implement checkoutEntries = checkoutManager.getMemberCheckoutRecord();
		return null;
	}

	public String getEntryId() {
		return checkoutRecordEntry.getEntryId();
	}
	public String getMemberId() {
		return libraryMember.getMemberId();
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public CheckoutRecordEntry getCheckoutRecordEntry() {
		return checkoutRecordEntry;
	}


}
