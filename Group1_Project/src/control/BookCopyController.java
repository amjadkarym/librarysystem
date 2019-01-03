package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import manager.BookCopyManager;
import manager.BookManager;
import model.domain.Book;
import model.domain.BookCopy;
import util.Util;

public class BookCopyController implements Initializable {

	@FXML private TextField sbIsbn;
	@FXML private TextField sbBookIsbn;
	@FXML private TextField sbBookTitle;
	@FXML private TextField sbBookStatus;
	@FXML private TextField noCopies;
	@FXML private Label errorMessage;
	@FXML private GridPane gridId;

	private BookManager bookManager;
	private BookCopyManager bookCopyManager;
	private Book book = null;

	@FXML
	public void searchBook() {
		book = null;
		errorMessage.setVisible(false);
		gridId.setVisible(false);
		String isbn = sbIsbn.getText();
		if(!Util.isExists(isbn)) {
			errorMessage.setVisible(true);
			errorMessage.setText("Isbn is required");
			return;
		}

		boolean isBook = false;

		Map<String, Book> bookMap = bookManager.getAll();
		Set set = bookMap.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;
		Book b = null;
		while(iterator.hasNext()) {
			e = iterator.next();
			b = (Book) e.getValue();
			if(b.getIsbn().equals(isbn)) {
				isBook = true;
				book = b;
			}
		}
		if(book != null) {
			gridId.setVisible(true);
			sbBookIsbn.setText(book.getIsbn());
			sbBookTitle.setText(book.getTitle());
			sbBookStatus.setText(book.getStatus()==true?"Available":"Not Available");
			sbIsbn.setText("");
		} else {
			sbBookIsbn.setText("");
			sbBookTitle.setText("");
			sbBookStatus.setText("");
			sbIsbn.setText("");
			errorMessage.setVisible(true);
			errorMessage.setText("Record not found");
			return;
		}
	}

	@SuppressWarnings("unused")
	@FXML
	public void addBookCopy() {
		errorMessage.setVisible(false);
		int copyNo = Integer.parseInt(noCopies.getText());
		if(!Util.isExists(copyNo)) {
			errorMessage.setVisible(true);
			errorMessage.setText("Provide the number of copies");
			return;
		}
//		BookCopy bookCopy = new BookCopy(book, copyNo, true);
//		List<BookCopy> listCopies = new ArrayList<BookCopy>();
//		int numOfCopies = book.getNumCopies();
//		for(int i=0; i<=copyNo; i++) {
//			BookCopy copy = new BookCopy(book, numOfCopies++, true);
//			listCopies.add(copy);
//		}
		for(int i=0; i<copyNo; i++) {
			book.addCopy();
		}

//		if(listCopies == null) {
//			errorMessage.setVisible(true);
//			errorMessage.setText("Provide the number of copies");
//			return;
//		}
//		book.updateCopies(bookCopy);
		bookManager.save(book);
//		bookCopyManager.saveList(listCopies);
		errorMessage.setVisible(true);
		errorMessage.setText(copyNo+" book Copies Added");
		noCopies.setText("");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gridId.setVisible(false);
		errorMessage.setVisible(false);
		bookManager = new BookManager();
		bookCopyManager = new BookCopyManager();
	}

}
