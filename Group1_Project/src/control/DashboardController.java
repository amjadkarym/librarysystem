package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable{

	@FXML
	AnchorPane ap;
	private String keyVal;
	@FXML Label viewBook;
	@FXML Label checkoutBook;
	@FXML Label checkoutActivities;
	@FXML Label lb;
	@FXML Pane pane;
	@FXML Label title;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Preferences userPreferences = Preferences.userRoot();
		keyVal = userPreferences.get("type", null);


		if(keyVal.equals("ADMIN")) {
			viewBook.setDisable(false);
			checkoutBook.setDisable(true);
			checkoutActivities.setDisable(true);
		}if( keyVal.equals("LIBRARIAN")) {
			viewBook.setDisable(true);
			checkoutBook.setDisable(false);
			checkoutActivities.setDisable(false);
		}
		if( keyVal.equals("BOTH")){
			checkoutBook.setDisable(false);
			viewBook.setDisable(false);
			checkoutActivities.setDisable(false);
		}
//		Parent allBooks = null; // we need to load the layout that we want to swap
		/*try {
			allBooks = (GridPane) FXMLLoader.load(getClass().getResource("/view/book/AllBooks.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title.setText("Dashboard");
		ap.getChildren().clear();

		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(allBooks);*/

	}


	@FXML
	public final EventHandler<? super MouseEvent> dueCheckout() throws IOException {

		Parent dueCheckout; // we need to load the layout that we want to swap
		dueCheckout = (GridPane) FXMLLoader.load(getClass().getResource("/view/book/CheckOutOverdue.fxml"));
		title.setText("Due Checkouts");
		ap.getChildren().clear();
		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(dueCheckout);

		return null;

	}
/*
	@FXML
	public final EventHandler<? super MouseEvent> addMember() throws IOException {

		Parent addMember; // we need to load the layout that we want to swap
		addMember = (GridPane) FXMLLoader.load(getClass().getResource("/view/member/AddMember.fxml"));
		title.setText("Add New Member");
		ap.getChildren().clear();
		ap.getChildren().add(addMember);

		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(addMember);
		return null;

	}*/

	@FXML
	public final EventHandler<? super MouseEvent> allBooks() throws IOException {

		Parent allBooks; // we need to load the layout that we want to swap
		allBooks = (GridPane) FXMLLoader.load(getClass().getResource("/view/book/AllBooks.fxml"));
		title.setText("View All Books");

		ap.getChildren().clear();
		ap.getChildren().add(allBooks);

		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(allBooks);
		return null;

	}

	@FXML
	public final EventHandler<? super MouseEvent> checkoutBook() throws IOException {

		Parent checkoutBook; // we need to load the layout that we want to swap
		checkoutBook = (GridPane) FXMLLoader.load(getClass().getResource("/view/book/CheckoutBook.fxml"));
		title.setText("Checkout Book");

		ap.getChildren().clear();


		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(checkoutBook);
		return null;

	}
	@FXML
	public final EventHandler<? super MouseEvent> checkoutActivities() throws IOException {

		Parent checkoutActivities; // we need to load the layout that we want to swap
		checkoutActivities = (GridPane) FXMLLoader.load(getClass().getResource("/view/CheckOutActivities.fxml"));
		title.setText("Checkout Activities");
		ap.getChildren().clear();

		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(checkoutActivities);


		return null;

	}

	@FXML
	public final EventHandler<? super MouseEvent> addBookCopy() throws IOException {

		Parent addBookCopy; // we need to load the layout that we want to swap
		addBookCopy = (GridPane) FXMLLoader.load(getClass().getResource("/view/book/AddBookCopy.fxml"));
		title.setText("Add Book Copy");
		ap.getChildren().clear();

		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(addBookCopy);


		return null;

	}


	@FXML
	public final EventHandler<? super MouseEvent> allMembers() throws IOException {

		Parent allMembers; // we need to load the layout that we want to swap
		allMembers = (GridPane) FXMLLoader.load(getClass().getResource("/view/member/AllMembers.fxml"));
		title.setText("View All Members");
		ap.getChildren().clear();

		ap.getChildren().add(title);
		ap.getChildren().add(pane);
		pane.getChildren().clear();
		pane.getChildren().add(allMembers);



		return null;

	}


	public String getKeyVal() {
		return keyVal;
	}

	public void setKeyVal(String keyVal) {
		this.keyVal = keyVal;
	}

}
