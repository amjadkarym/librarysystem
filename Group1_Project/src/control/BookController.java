package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import manager.AuthorManager;
import manager.BookManager;
import manager.AuthorManager;
import model.dataaccess.DataAccessFacade;
import model.domain.Author;
import model.domain.Book;
import util.Util;

public class BookController implements Initializable {

	@FXML
	TextField sbIsbn;
	@FXML
	TextField isbnVal;
	@FXML
	TextField searchVal;
	@FXML
	TextField sbTitle;
	@FXML
	TextField sbMaxCheckoutLength;
	@FXML
	Label sbAvailability;
	@FXML
	ListView<Author> sbAuthors;
	@FXML
	ListView<Book> sbBooks;
	
	@FXML
	private Label errorMessage;
	
	@FXML
	private TableView<Book> booksTable = new TableView<Book>();

	@FXML
	private TableColumn<Book, String> isbnCol = new TableColumn<Book, String>();
	@FXML
	private TableColumn<Book, String> titleCol = new TableColumn<Book, String>();
	@FXML
	private TableColumn<Book, String> maxLenCol = new TableColumn<Book, String>();
	@FXML
	private TableColumn<Book, String> statusCol = new TableColumn<Book, String>();

	List<Author> authorsList = new ArrayList<Author>();
	ObservableList<Book> booksList = FXCollections.observableArrayList();
	HashMap<String, Author> authorMap;
	HashMap<String, Book> bookMap;
	Book book;
	List<String> columns = new ArrayList<String>();
	List<String> rows = new ArrayList<String>();
	ObservableList<ObservableList<String>> csvData = FXCollections.observableArrayList();

//	private DataAccessFacade dataAccessFacade = new DataAccessFacade();
	private AuthorManager authorManager = new AuthorManager();
	private BookManager bookManager = new BookManager();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		sbAuthors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		errorMessage.setVisible(false);
		this.setBooks();
		authorMap = (HashMap<String, Author>) authorManager.getAll();//dataAccessFacade.readAuthorMap();
		this.setAuthors();
		this.showData();
	}

	public void setAuthors() {
		Set set = authorMap.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;
		Author author;
		int i = 0;
		while (iterator.hasNext()) {
			e = iterator.next();
			author = (Author) e.getValue();
			sbAuthors.getItems().add(author);
			authorsList.add(author);
		}
	}
	
	
	@FXML
	public void showData()
	{
		
		booksTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		           sbIsbn.setText(booksTable.getSelectionModel().getSelectedItem().getIsbn()); 
		           isbnVal.setText(booksTable.getSelectionModel().getSelectedItem().getIsbn()); 
		           sbTitle.setText(booksTable.getSelectionModel().getSelectedItem().getTitle()); 
		           sbMaxCheckoutLength.setText(String.valueOf(booksTable.getSelectionModel().getSelectedItem().getMaxCheckoutLength()));
		           sbTitle.setText(booksTable.getSelectionModel().getSelectedItem().getTitle());
		           sbAvailability.setText(booksTable.getSelectionModel().getSelectedItem().getStatus().toString());
		           List<Author> list = booksTable.getSelectionModel().getSelectedItem().getAuthors();
		           sbAuthors.getSelectionModel().clearSelection();
		           	Iterator<Author> iterator = list.iterator() ;
		   			Author author;
		   			
		   			while (iterator.hasNext()) {
		   			author = iterator.next();
		   			
		   			if (authorMap.containsKey(author.getFullName())) {
		   				
		   				
		   				
		   				sbAuthors.getSelectionModel().select(authorsList.indexOf(author));
		   				
		   				//System.out.println(authorsList.indexOf(author));
		   			}
		   		}
		        }
		    }
		});
	}
		
		@FXML
		public void onUpdate(ActionEvent events)
		{
			bookMap = (HashMap<String, Book>) bookManager.getAll();//dataAccessFacade.readBooksMap();
			bookMap.remove(isbnVal.getText());
			addBook(events);
			booksList.removeAll(booksList);
			setBooks();
			
	
		}
		
		@FXML
		public void onDelete(ActionEvent events)
		{	
			bookMap = (HashMap<String, Book>) bookManager.getAll();//dataAccessFacade.readBooksMap();
			bookMap.remove(isbnVal.getText());
			booksList.removeAll(booksList);
			booksList.addAll(bookMap.values());
			
			bookManager.saveList(booksList);
			booksList.removeAll(booksList);
			setBooks();
			resetFields();
			
	
		}

	@FXML
	public void addBook(ActionEvent events) {
		errorMessage.setVisible(false);
		/*
		 * String isbn = sbIsbn.getText(); String title = sbTitle.getText(); int
		 * maxCheckoutLength = Integer.parseInt(sbMaxCheckoutLength.getText());
		 *
		 * Book book = new Book(isbn, title, maxCheckoutLength,
		 * sbAuthors.getItems()); dataAccessFacade.saveNewBook(book);
		 */

		String isbn = sbIsbn.getText();
		String title = sbTitle.getText();
		int maxCheckoutLength = 0;
		if (!Util.isExists(isbn) || !Util.isExists(title) || !Util.isExists(maxCheckoutLength)) {
			errorMessage.setVisible(true);
			errorMessage.setText("Mandatory field cannot be empty");
			return;
		} else {
			try {
				maxCheckoutLength = Integer.parseInt(sbMaxCheckoutLength.getText());
			} catch (NumberFormatException e) {
				errorMessage.setVisible(true);
				errorMessage.setText("Type Format Exception");
				return;
			}
			
		}
		
		List<Author> list = new ArrayList<Author>();
		List<Author> authorsSel = sbAuthors.getSelectionModel().getSelectedItems();
		Set set = authorMap.entrySet();
		Iterator<Author> iterator = authorsSel.iterator();
		
		Author author;
		while (iterator.hasNext()) {
			author = iterator.next();
			if (authorMap.containsKey(author.getFullName())) {
				System.out.println(author.getFullName());
				list.add(author);
				
			}
		}

		Book book = new Book(isbn, title, maxCheckoutLength, list);
		// System.out.println(book.toString());
		bookManager.save(book);
		booksList.removeAll(booksList);
		setBooks();
		resetFields();
		//dataAccessFacade.saveNewBook(book);
		
		
		
		
		
	}

	public void setBooks() {
		bookMap = (HashMap<String, Book>) bookManager.getAll();//dataAccessFacade.readBooksMap();
		Set set = bookMap.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;

		while (iterator.hasNext()) {
			e = iterator.next();
			book = (Book) e.getValue();
			booksList.add(book);

		}
		isbnCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		maxLenCol.setCellValueFactory(new PropertyValueFactory<Book, String>("maxCheckoutLength"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));

		booksTable.setItems(booksList);

	}

	
	public void resetFields() {
		sbIsbn.setText("");
		sbTitle.setText("");
		sbMaxCheckoutLength.setText("");
		isbnVal.setText("");
		sbAvailability.setText("Availability");
		//sbAuthors.setValue(null);
	}
	
	public void  search() {
		FilteredList<Book> flBook = new FilteredList(booksList, b -> true);//Pass the data to a filtered list
        booksTable.setItems(flBook);//Set the table's items using the filtered list
        //booksTable.getColumns().addAll(isbnCol, titleCol, maxLenCol, statusCol);
        flBook.setPredicate(b -> b.getIsbn().toLowerCase().contains(searchVal.getText().toLowerCase().trim()));//filter table by first name

	}
	

}
