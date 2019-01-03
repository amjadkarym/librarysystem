package model.dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import model.domain.Address;
import model.domain.Author;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.CheckoutRecord;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import model.domain.User;
import util.Util;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, AUTHORS, ADDRESS, BOOKCOPY, CHECKOUT_RECORD_ENTRY, CHECKOUT_RECORD;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "/src/model/dataaccess/storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// book
	// =========================
	@Override
	public void saveNewBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		books.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, books);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	public void saveBookList(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	// checkout record

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap() {
		return (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.CHECKOUT_RECORD);
	}

	@Override
	public void saveNewCheckoutRecord(CheckoutRecord checkoutRecord) {
		HashMap<String, CheckoutRecord> checkoutRecordEntries = readCheckoutRecordMap();
		if(!Util.isExists(checkoutRecordEntries)) {
			checkoutRecordEntries = new HashMap<String, CheckoutRecord>();
		}
		checkoutRecordEntries.put(checkoutRecord.getEntryId(), checkoutRecord);
		saveToStorage(StorageType.CHECKOUT_RECORD, checkoutRecordEntries);
	}

	// checkout record entry
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, CheckoutRecordEntry> readCheckoutRecordEntryMap() {
		return (HashMap<String, CheckoutRecordEntry>) readFromStorage(StorageType.CHECKOUT_RECORD_ENTRY);
	}

	@Override
	public void saveNewCheckoutRecordEntry(CheckoutRecordEntry checkoutRecordEntry) {
		HashMap<String, CheckoutRecordEntry> checkoutRecordEntries = readCheckoutRecordEntryMap();
		if(!Util.isExists(checkoutRecordEntries)) {
			checkoutRecordEntries = new HashMap<String, CheckoutRecordEntry>();
		}
		checkoutRecordEntries.put(checkoutRecordEntry.getEntryId(), checkoutRecordEntry);
		saveToStorage(StorageType.CHECKOUT_RECORD_ENTRY, checkoutRecordEntries);

	}
	// book copy
	// ========================


	/*@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, BookCopy> readBookCopiesMap() {
		return (HashMap<String, BookCopy>) readFromStorage(StorageType.BOOKCOPY);
	}
*/
	@Override
	public void saveNewBookCopy(BookCopy bookCopy) {
		HashMap<String, BookCopy> bookCopies = readBookCopyMap();
		if(!Util.isExists(bookCopies)) {
			bookCopies = new HashMap<String, BookCopy>();
		}
		bookCopies.put(String.valueOf(bookCopy.getCopyNum()), bookCopy);
		saveToStorage(StorageType.BOOKCOPY, bookCopies);
	}

	static void loadBookCopyMap(List<BookCopy> bookCopyList) {
		HashMap<String, BookCopy> bookCopies = new HashMap<String, BookCopy>();
		bookCopyList.forEach(bookCopy -> bookCopies.put(String.valueOf(bookCopy.getCopyNum()), bookCopy));
		saveToStorage(StorageType.BOOKCOPY, bookCopies);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, BookCopy> readBookCopyMap() {
		return (HashMap<String, BookCopy>) readFromStorage(StorageType.BOOKCOPY);
	}

	// member
	// =========================
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	// implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	public static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	// author
	// =========================
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Author> readAuthorMap() {
		return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup
	// static void loadMemberMap(List<LibraryMember> memberList) {

	public static void loadAuthorMap(List<Author> authorList) {
		HashMap<String, Author> authors = new HashMap<String, Author>();
		authorList.forEach(author -> authors.put(author.getFullName(), author));
		saveToStorage(StorageType.AUTHORS, authors);
	}

	@Override
	public void saveNewAuthor(Author author) {
		HashMap<String, Author> authors = readAuthorMap();
		String fname = author.getFirstName();
		authors.put(fname, author);
		saveToStorage(StorageType.AUTHORS, authors);

	}

	// user
	// =========================
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}


	@Override
	public void saveNewUser(User user) {
		HashMap<String, User> users = readUserMap();
		users.put(user.getId(), user);
		saveToStorage(StorageType.USERS, users);

	}

	// address
	// ===========================
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Address> readAddress() {
		return (HashMap<String, Address>) readFromStorage(StorageType.ADDRESS);
	}


	public static void loadAddressMap(List<Address> addressList) {
		HashMap<String, Address> addresses = new HashMap<String, Address>();
		addressList.forEach(address -> addresses.put(address.getZip(), address));
		saveToStorage(StorageType.ADDRESS, addresses);
	}

	@Override
	public void saveNewAddress(Address address) {
		HashMap<String, Address> addresses = readAddress();
		String zip = address.getZip();
		addresses.put(zip, address);
		saveToStorage(StorageType.ADDRESS, addresses);

	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}


}
