package control;



import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import manager.AddressManager;
import manager.AuthorManager;
import manager.BookManager;
import manager.LibraryMemberManager;
import model.dataaccess.DataAccessFacade;
import model.domain.Address;
import model.domain.Author;
import model.domain.Book;
import model.domain.LibraryMember;
import model.domain.Person;
import util.Util;

public class MemberController implements Initializable {
	//Member
	@FXML TextField sbMemberID;
	@FXML TextField sbFirstName;
	@FXML TextField sbLastName;
	@FXML TextField sbTell;
	//Address
	@FXML TextField sbStreet;
	@FXML TextField sbCity;
	@FXML TextField sbState;
	@FXML TextField sbZip;
	@FXML
	TextField searchVal;

	private DataAccessFacade dataAccessFacade = new DataAccessFacade();
/*
	@FXML
	public void addMember(ActionEvent events) {

		String memberId = sbMemberID.getText();
		String fname = sbFirstName.getText();
		String lname = sbLastName.getText();
		String tell = sbTell.getText();

		String street = sbStreet.getText();
		String city = sbCity.getText();
		String state = sbState.getText();
		String zip = sbZip.getText();

		Address address = new Address(street, city, state, zip);
		LibraryMember lmember = new LibraryMember(memberId, fname, lname, tell, address);


		dataAccessFacade.saveNewAddress(address);
		dataAccessFacade.saveNewMember(lmember);

	}
	*/
	
	
	@FXML
	private Label errorMessage;
	
	@FXML
	private TableView<LibraryMember> membersTable = new TableView<LibraryMember>();

	@FXML
	private TableColumn<Person, String> sbMember = new TableColumn<Person, String>();
	@FXML
	private TableColumn<Person, String> sbFname = new TableColumn<Person, String>();
	@FXML
	private TableColumn<Person, String> sbLname = new TableColumn<Person, String>();
	@FXML
	private TableColumn<Person, String> sbPhone = new TableColumn<Person, String>();


	ObservableList<LibraryMember> membersList = FXCollections.observableArrayList();;
	HashMap<String, LibraryMember> membersMap;
	HashMap<String, LibraryMember> members;
	
	LibraryMember member;
	
//	private DataAccessFacade dataAccessFacade = new DataAccessFacade();

	private LibraryMemberManager memberManager = new LibraryMemberManager();
	private AddressManager addressManager = new AddressManager();
	private int lastId;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		errorMessage.setVisible(false);
		
		
		this.setMembers();
		members =  (HashMap<String,LibraryMember>) memberManager.getAll();//dataAccessFacade.readBooksMap();
		lastId = members.size()+1;
		sbMemberID.setText(""+lastId);
		
		this.showData();
	}

	
	
	
	@FXML
	public void showData()
	{
		
		membersTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	sbMemberID.setText(membersTable.getSelectionModel().getSelectedItem().getMemberId()); 
		        	sbFirstName.setText(membersTable.getSelectionModel().getSelectedItem().getFirstName()); 
		        	sbLastName.setText(membersTable.getSelectionModel().getSelectedItem().getLastName()); 
		        	sbTell.setText(membersTable.getSelectionModel().getSelectedItem().getTelephone());
		        	sbStreet.setText(membersTable.getSelectionModel().getSelectedItem().getAddress().getStreet());
		        	sbCity.setText(membersTable.getSelectionModel().getSelectedItem().getAddress().getCity());
		        	sbState.setText(membersTable.getSelectionModel().getSelectedItem().getAddress().getState());
		        	sbZip.setText(membersTable.getSelectionModel().getSelectedItem().getAddress().getZip());
		        	
		        	
		           		        }
		    }
		});
	}
		
		@FXML
		public void onUpdate(ActionEvent events)
		{
			membersMap = (HashMap<String, LibraryMember>) memberManager.getAll();//dataAccessFacade.readBooksMap();
			membersMap.remove(sbMemberID.getText());
			addMember(events);
			membersList.removeAll(membersList);
			setMembers();
			
	
		}
		
		@FXML
		public void onDelete(ActionEvent events)
		{	
			membersMap = (HashMap<String, LibraryMember>) memberManager.getAll();//dataAccessFacade.readBooksMap();
			membersMap.remove(sbMemberID.getText());
			membersList.removeAll(membersList);
			membersList.addAll(membersMap.values());
			
			memberManager.saveList(membersList);
			membersList.removeAll(membersList);
			setMembers();
			resetFields();
			
	
		}

	@FXML
	public void addMember(ActionEvent events) {
		errorMessage.setVisible(false);
		/*
		 * String isbn = sbIsbn.getText(); String title = sbTitle.getText(); int
		 * maxCheckoutLength = Integer.parseInt(sbMaxCheckoutLength.getText());
		 *
		 * Book book = new Book(isbn, title, maxCheckoutLength,
		 * sbAuthors.getItems()); dataAccessFacade.saveNewBook(book);
		 */
		String memberId = sbMemberID.getText();
		String fname = sbFirstName.getText();
		String lname = sbLastName.getText();
		String tell = sbTell.getText();

		String street = sbStreet.getText();
		String city = sbCity.getText();
		String state = sbState.getText();
		String zip = sbZip.getText();
		int zipCode = 0;
		
		if (!Util.isExists(memberId) || !Util.isExists(fname) || !Util.isExists(lname) || !Util.isExists(tell)
				|| !Util.isExists(street) || !Util.isExists(city) || !Util.isExists(state) || !Util.isExists(state)) {
			errorMessage.setVisible(true);
			errorMessage.setText("Mandatory field cannot be empty");
			return;
		} else {
			try {
				zipCode = Integer.parseInt(zip);
			} catch (NumberFormatException e) {
				errorMessage.setVisible(true);
				errorMessage.setText("Type Format Exception");
				return;
			}
			
		}
		
		Address address = new Address(street, city, state, zip);
		LibraryMember lmember = new LibraryMember(memberId, fname, lname, tell, address);

		addressManager.save(address);

		
		memberManager.save(lmember);
		membersList.removeAll(membersList);
		setMembers();
		resetFields();
		//dataAccessFacade.saveNewBook(book);
		
		
		
		
		
	}

	public void setMembers() {
		membersMap = (HashMap<String, LibraryMember>) memberManager.getAll();//dataAccessFacade.readBooksMap();
		Set set = membersMap.entrySet();
		Iterator<Entry> iterator = set.iterator();
		Map.Entry e;

		while (iterator.hasNext()) {
			e = iterator.next();
			member = (LibraryMember) e.getValue();
			membersList.add(member);

		}
		sbMember.setCellValueFactory(new PropertyValueFactory<Person, String>("memberId"));
		sbFname.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		sbLname.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		sbPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("telephone"));

		membersTable.setItems(membersList);

	}

	
	public void resetFields() {
		members =  (HashMap<String,LibraryMember>) memberManager.getAll();
		lastId = members.size()+1;
		sbMemberID.setText(""+lastId);
		sbFirstName.setText("");
		sbLastName.setText("");
		sbTell.setText("");

		sbStreet.setText("");
		sbCity.setText("");
		sbState.setText("");
		sbZip.setText("");
	}
	
	public void  search() {
		FilteredList<LibraryMember> flMember = new FilteredList(membersList, m -> true);//Pass the data to a filtered list
        membersTable.setItems(flMember);//Set the table's items using the filtered list
        //booksTable.getColumns().addAll(isbnCol, titleCol, maxLenCol, statusCol);
        flMember.setPredicate(m -> m.getMemberId().toLowerCase().contains(searchVal.getText().toLowerCase().trim()));//filter table by first name

	}
	


}
