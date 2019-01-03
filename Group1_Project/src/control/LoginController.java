package control;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.prefs.Preferences;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.dataaccess.DataAccessFacade;
import model.domain.Author;
import model.domain.User;

public class LoginController implements Initializable {

	HashMap<String, User> users;
	@FXML
	private Label error_msg;
	@FXML
	private Button login;
	@FXML
	private TextField userid;
	@FXML
	private TextField password;

	private DataAccessFacade dataAccessFacade = new DataAccessFacade();



	@FXML public void login(ActionEvent event) throws IOException {
		String user_id = userid.getText();
		String user_password = password.getText();



		Set<Entry<String, User>> set = users.entrySet();
		Map.Entry<String, User> e;
		Iterator<Entry<String, User>> iterator = set.iterator();
		User user = new User();

		while (iterator.hasNext()) {
			e = iterator.next();
			user = e.getValue();
			if (user.getId().equals(user_id) && user.getPassword().equals(user_password)) {
				Preferences userPreferences = Preferences.userRoot();
				userPreferences.put("type", user.getAuthorization().toString());
				System.out.println("Login");

				GridPane root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
				Scene scene = new Scene(root, 700, 500);

				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.close();
				primaryStage.setMaximized(true);
				primaryStage.setScene(scene);

				primaryStage.show();

			}

		}

		System.out.println("out of loop, user not found");

//		System.out.println("user nmae" + userid.getText());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = dataAccessFacade.readUserMap();
		Map<String, Author> authorMap = dataAccessFacade.readAuthorMap();
	}




}
