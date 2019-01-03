package control;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import manager.BookCopyManager;
import manager.BookManager;
import manager.CheckoutRecordEntryManager;
import manager.CheckoutRecordManager;
import manager.LibraryMemberManager;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.CheckoutRecord;
import model.domain.CheckoutRecordDto;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import model.domain.Person;
import util.Util;

@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
public class CheckOutBookController implements Initializable {

	@FXML private TextField sbMemberId;
	@FXML private TextField sbIsbn;
	@FXML private Label errorMessage;

	private CheckoutRecordEntryManager checkoutRecordEntryManager;
	private CheckoutRecordManager checkoutRecordManager;
	private BookManager bookManager;
	private LibraryMemberManager libraryManager;
	private CheckoutRecord record;
	HashMap<String, Book> bookMap;
	HashMap<String, LibraryMember> memberMap;
	HashMap<String, CheckoutRecordEntry> checkOutRecordEntryMap;
	ObservableList<CheckoutRecordDto> recordsList = FXCollections.observableArrayList();
	@FXML
	private TableView checkOutTable = new TableView();

	@FXML
	private TableColumn<CheckoutRecordDto, String> sbIsbnCol = new TableColumn<CheckoutRecordDto, String>();
	@FXML
	private TableColumn<CheckoutRecordDto, String> sbTitleCol = new TableColumn<CheckoutRecordDto, String>();

	@FXML
	private TableColumn<CheckoutRecordDto, String> sbMemberNamesCol = new TableColumn<CheckoutRecordDto, String>();

	@FXML
	private TableColumn<CheckoutRecordDto, String> sbMemberIdCol = new TableColumn<CheckoutRecordDto, String>();

	private ObservableList<Person> membersList = FXCollections.observableArrayList();



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		checkoutRecordEntryManager = new CheckoutRecordEntryManager();
		checkoutRecordManager = new CheckoutRecordManager();
		bookManager = new BookManager();
		libraryManager = new LibraryMemberManager();
		setCheckOutEntries();

	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	public void setCheckOutEntries() {
		Map<String, CheckoutRecord> records = checkoutRecordManager.getAll();
		if(Util.isExists(records)) {
		Set set = records.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;
		String memberId, entryId;


		while (iterator.hasNext()) {
			e = iterator.next();
			record = (CheckoutRecord) e.getValue();
			memberId = record.getMemberId();
			entryId = record.getEntryId();
			LibraryMember member = getMember(memberId);
			Book book = getBook(entryId);
			CheckoutRecordDto recordDTO = new CheckoutRecordDto();
			recordDTO.setFullName(member.getFullName());
			recordDTO.setIsbn(book.getIsbn());
			recordDTO.setTitle(book.getTitle());
			recordDTO.setMemberId(memberId);
			recordsList.add(recordDTO);

		}



		sbIsbnCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("isbn"));
		sbTitleCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("title"));
		sbMemberIdCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("memberId"));
		sbMemberNamesCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("fullName"));



		checkOutTable.setItems(recordsList);
		//checkOutTable.setItems(membersList);
		}

	}

	public LibraryMember getMember(String memberId) {

		memberMap = (HashMap<String, LibraryMember>) libraryManager.getAll();
		Set set = memberMap.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;
		LibraryMember member = null;
		while (iterator.hasNext()) {
			e = iterator.next();
			member = (LibraryMember) e.getValue();
			if(member.getMemberId().equals(memberId)) {
				return member;
			}

		}

		return null;
	}

	public Book getBook(String entryId) {

		checkOutRecordEntryMap = (HashMap<String, CheckoutRecordEntry>) checkoutRecordEntryManager.getAll();
		Set set = checkOutRecordEntryMap.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;
		Book book;
		CheckoutRecordEntry entry = null;
		while (iterator.hasNext()) {
			e = iterator.next();
			entry = (CheckoutRecordEntry) e.getValue();
			if(entry.getEntryId().equals(entryId)) {
				book = entry.getBookCopy().getBook();
				return book;
			}

		}

		return null;
	}


	@SuppressWarnings("unused")
	@FXML
	public void checkout() {
		String memberId = sbMemberId.getText();
		String isbn = sbIsbn.getText();

		if(!Util.isExists(memberId) || !Util.isExists(isbn)) {
			errorMessage.setVisible(true);
			errorMessage.setText("All fields are required");
		} else {
			LibraryMember member = searchLibraryMember(memberId);
			Book book = searchBook(isbn);

			if(book == null) {
				errorMessage.setVisible(true);
				errorMessage.setText("Book not exist");
				return;
			}
			BookCopy[] copies = book.getCopies();
			boolean isAvailable = copies.length > 0 ? true : false;
			BookCopy bookCopy = new BookCopy(book, copies.length, isAvailable);
			if(member == null) {
				errorMessage.setVisible(true);
				errorMessage.setText("Member not exist");
				return;
			}

			if(copies.length < 1) {
				errorMessage.setVisible(true);
				errorMessage.setText("Book not availabe");
				return;
			}
			Date dueDate = Util.currentDatePlusDays(book.getMaxCheckoutLength());
			CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(bookCopy, new Date(), dueDate);
			checkoutRecordEntryManager.save(checkoutRecordEntry);

			CheckoutRecord checkoutRecord = new CheckoutRecord(member, checkoutRecordEntry);
			checkoutRecordManager.save(checkoutRecord);
			recordsList.removeAll(recordsList);
			setCheckOutEntries();
			resetFields();
			errorMessage.setVisible(false);

		}
	}

	public void resetFields() {
		sbIsbn.setText("");
		sbMemberId.setText("");
	}

	private Book searchBook(String isbn) {
		Map<String, Book> map = bookManager.getAll();
		if(map.get(isbn) != null)
			return map.get(isbn);
		return null;
	}

	private LibraryMember searchLibraryMember(String memberId) {
		Map<String, LibraryMember> map = libraryManager.getAll();
		if(map.get(memberId) != null)
			return map.get(memberId);
		return null;
	}


}
