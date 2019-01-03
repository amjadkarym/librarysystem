package control;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import manager.BookManager;
import manager.CheckoutRecordEntryManager;
import manager.CheckoutRecordManager;
import manager.LibraryMemberManager;
import model.domain.Book;
import model.domain.CheckoutRecord;
import model.domain.CheckoutRecordDto;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import model.domain.Person;
import util.Util;

public class CheckoutActivityController implements Initializable {

	@FXML private TextField sbMemberId;
	@FXML private Label errorMessage;
	@FXML private Label sbFirstName;
	@FXML private Label sbLastName;
	@FXML private Label sbTel;
	@FXML private GridPane gridId;

	@FXML
	private TableView checkOutTable = new TableView();

	//ObservableList<CheckoutRecord> recordsList;
	ObservableList<CheckoutRecordDto> recordsList = FXCollections.observableArrayList();
	@FXML
	private TableColumn<CheckoutRecordDto, String> sbIsbnCol = new TableColumn<CheckoutRecordDto, String>();
	@FXML
	private TableColumn<CheckoutRecordDto, String> sbTitleCol = new TableColumn<CheckoutRecordDto, String>();

	@FXML
	private TableColumn<CheckoutRecordDto, String> sbMemberNamesCol = new TableColumn<CheckoutRecordDto, String>();

	@FXML
	private TableColumn<CheckoutRecordDto, String> sbMemberIdCol = new TableColumn<CheckoutRecordDto, String>();

	private Map<String, CheckoutRecord> records;
	HashMap<String, CheckoutRecordEntry> checkOutRecordEntryMap;
	private CheckoutRecordManager checkoutRecordManager;
	private BookManager bookManager;
	private LibraryMemberManager libraryManager;
	private CheckoutRecord record;
	private LibraryMemberManager libraryMemberManager;
	private ObservableList<Person> membersList = FXCollections.observableArrayList();
	Map<String, LibraryMember> memberMap;
	private CheckoutRecordEntryManager checkoutRecordEntryManager;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bookManager = new BookManager();
		checkoutRecordEntryManager = new CheckoutRecordEntryManager();
		checkoutRecordManager = new CheckoutRecordManager();
		records = checkoutRecordManager.getAll();
		libraryMemberManager = new LibraryMemberManager();
		gridId.setVisible(false);
//		setCheckOutEntries();
	}

	@FXML
	public void searchMember() {
		recordsList = FXCollections.observableArrayList();
		sbFirstName.setText("");
		sbLastName.setText("");
		sbTel.setText("");
		gridId.setVisible(true);
		String memberId = sbMemberId.getText();

		if(!Util.isExists(memberId)) {
			errorMessage.setVisible(true);
			errorMessage.setText("Provide member id");
			return;
		}
		Map<String, CheckoutRecord> records = checkoutRecordManager.getAll();
		if(Util.isExists(records)) {
		Set set = records.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;
		String memberId1 = null, entryId;
		


		while (iterator.hasNext()) {
			e = iterator.next();
			record = (CheckoutRecord) e.getValue();
			memberId1 = record.getMemberId();
			entryId = record.getEntryId();
			LibraryMember member = getMember(memberId);
			Book book = getBook(entryId);
			CheckoutRecordDto recordDTO = new CheckoutRecordDto();
			recordDTO.setFullName(member.getFullName());
			recordDTO.setIsbn(book.getIsbn());
			recordDTO.setTitle(book.getTitle());
			recordDTO.setMemberId(member.getMemberId());
			recordsList.add(recordDTO);

		}


		

		memberMap = libraryMemberManager.getAll();
		Set set1 = memberMap.entrySet();
		Iterator<Entry> iterator1 = set1.iterator();
		Map.Entry e1;
		LibraryMember m = null;
		LibraryMember member = null;
		while(iterator1.hasNext()){
			e1 = iterator1.next();
			m = (LibraryMember) e1.getValue();
			if(memberId1.equals(m.getMemberId())) {
				member = m;
				break;
			}
		}
		if(member != null) {
			sbFirstName.setText(member.getFirstName());
			sbLastName.setText(member.getLastName());
			sbTel.setText(member.getTelephone());
		} else {
			gridId.setVisible(false);
		}

		setCheckOutEntries();
		
		}

	}
	
	public LibraryMember getMember(String memberId) {

		memberMap = (HashMap<String, LibraryMember>) libraryMemberManager.getAll();
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

	public void resetFields() {
		sbMemberId.setText("");
	}

	public void setCheckOutEntries() {
		resetFields();
		
		sbIsbnCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("isbn"));
		sbTitleCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("title"));
		sbMemberIdCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("memberId"));
		sbMemberNamesCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecordDto, String>("fullName"));



		checkOutTable.setItems(recordsList);
		//checkOutTable.setItems(membersList);
		}


	

}
