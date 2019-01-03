package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import manager.BookManager;
import manager.CheckoutRecordEntryManager;
import manager.CheckoutRecordManager;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.CheckoutOverdueDto;
import model.domain.CheckoutRecord;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import util.Util;

public class CheckOutOverdueController implements Initializable {

	@FXML
	private TextField sbBookIsbn;
	@FXML
	private Label errorMessage;

	@FXML
	private TableView<CheckoutOverdueDto> checkoutOverDueTable = new TableView<CheckoutOverdueDto>();
	@FXML
	private TableColumn<CheckoutOverdueDto, String> sbBookIsbnCol = new TableColumn<CheckoutOverdueDto, String>();
	@FXML
	private TableColumn<CheckoutOverdueDto, String> sbBookTitleCol = new TableColumn<CheckoutOverdueDto, String>();
	@FXML
	private TableColumn<CheckoutOverdueDto, String> sbBookCopyNoCol = new TableColumn<CheckoutOverdueDto, String>();
	@FXML
	private TableColumn<CheckoutOverdueDto, String> sbMemberNameCol = new TableColumn<CheckoutOverdueDto, String>();
	@FXML
	private TableColumn<CheckoutOverdueDto, String> sbDueDate = new TableColumn<CheckoutOverdueDto, String>();
	private BookManager bookManager;
	private CheckoutRecordManager checkoutRecordManager;
	private CheckoutRecordEntryManager checkoutRecordEntryManager;

	ObservableList<CheckoutOverdueDto> recordsList;

	@FXML
	private TableView<CheckoutOverdueDto> checkOutOverDueTable = new TableView<CheckoutOverdueDto>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bookManager = new BookManager();
		checkoutRecordManager = new CheckoutRecordManager();
		checkoutRecordEntryManager = new CheckoutRecordEntryManager();
	}

	@FXML
	public void searchBook() {
		recordsList = FXCollections.observableArrayList();
		String isbn = sbBookIsbn.getText();
		if (!Util.isExists(isbn)) {
			errorMessage.setVisible(true);
			errorMessage.setText("Provide Isbn");
			return;
		}
		Book book = searchBook(isbn);
		if(book == null) {
			errorMessage.setVisible(true);
			errorMessage.setText("No data");
			return;
		}
		BookCopy[] copies = book.getCopies();

		List<String> entries = new ArrayList<String>();

		Map<String, CheckoutRecordEntry> entryMap = checkoutRecordEntryManager.getAll();
		if (Util.isExists(entryMap)) {
			Set set1 = entryMap.entrySet();
			Map.Entry<String, CheckoutRecordEntry> e1;
			CheckoutRecordEntry entry;
			Iterator<Entry> iterator1 = set1.iterator();
			while (iterator1.hasNext()) {
				e1 = iterator1.next();
				entry = e1.getValue();
				for (int i = 0; i < copies.length; i++) {
					if (entry.getBookCopy().equals(copies[i])) {
						entries.add(entry.getEntryId());
					}
				}
			}

			List<CheckoutRecord> records = new ArrayList<CheckoutRecord>();

			Map<String, CheckoutRecord> recordEntryMap = checkoutRecordManager.getAll();
			Set set = recordEntryMap.entrySet();
			Map.Entry<String, CheckoutRecord> e;
			CheckoutRecord record;
			Iterator<Entry> iterator = set.iterator();
			LibraryMember member;
			while (iterator.hasNext()) {
				e = iterator.next();
				record = e.getValue();
				for (int i = 0; i < entries.size(); i++) {
					if (record.getEntryId().equals(entries.get(i))) {
						records.add(record);
					}
				}

			}

			Iterator<CheckoutRecord> checkoutlist = records.iterator();
			CheckoutRecord c;
			CheckoutOverdueDto checkoutOverDueDto = new CheckoutOverdueDto();
			while (checkoutlist.hasNext()) {
				c = checkoutlist.next();
				checkoutOverDueDto.setMemberName(c.getLibraryMember().getFullName());
				checkoutOverDueDto.setDueDate(c.getCheckoutRecordEntry().getDueDate().toString());
				checkoutOverDueDto.setTitle(book.getTitle());
				checkoutOverDueDto.setIsbn(book.getIsbn());
				checkoutOverDueDto.setBookCopyNo(c.getCheckoutRecordEntry().getBookCopy().getBookCopyId());
				recordsList.add(checkoutOverDueDto);
			}
			if(!Util.isExists(recordsList)) {
				errorMessage.setVisible(true);
				errorMessage.setText("No data");
				return;
			}
			setCheckOutEntries();
		}
	}

	public void setCheckOutEntries() {
		sbBookIsbnCol.setCellValueFactory(new PropertyValueFactory<CheckoutOverdueDto, String>("isbn"));
		sbBookTitleCol.setCellValueFactory(new PropertyValueFactory<CheckoutOverdueDto, String>("title"));
		sbBookCopyNoCol.setCellValueFactory(new PropertyValueFactory<CheckoutOverdueDto, String>("bookCopyNo"));
		sbMemberNameCol.setCellValueFactory(new PropertyValueFactory<CheckoutOverdueDto, String>("memberName"));
		sbDueDate.setCellValueFactory(new PropertyValueFactory<CheckoutOverdueDto, String>("dueDate"));

		checkOutOverDueTable.setItems(recordsList);

	}

	private Book searchBook(String isbn) {
		Map<String, Book> map = bookManager.getAll();
		if (map.get(isbn) != null)
			return map.get(isbn);
		return null;
	}

}
