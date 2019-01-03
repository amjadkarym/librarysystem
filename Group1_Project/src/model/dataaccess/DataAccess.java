package model.dataaccess;

import java.util.HashMap;

import model.domain.Address;
import model.domain.Author;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.CheckoutRecord;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import model.domain.User;

public interface DataAccess {
	public HashMap<String,Book> readBooksMap();
	public void saveNewBook(Book book);
	
	public HashMap<String,CheckoutRecordEntry> readCheckoutRecordEntryMap();
	public void saveNewCheckoutRecordEntry(CheckoutRecordEntry checkoutRecordEntry);
	
	public HashMap<String,CheckoutRecord> readCheckoutRecordMap();
	public void saveNewCheckoutRecord(CheckoutRecord checkoutRecord);

	public HashMap<String,BookCopy> readBookCopyMap();
	public void saveNewBookCopy(BookCopy bookCopy);

	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member);

	public HashMap<String,User> readUserMap();
	public void saveNewUser(User user);

	public HashMap<String, Author> readAuthorMap();
	public void saveNewAuthor(Author author);

	public HashMap<String, Address> readAddress();
	public void saveNewAddress(Address address);


}
