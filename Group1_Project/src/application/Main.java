package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private GridPane mainLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("");
		showMainView();
	}

	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/view/Login.fxml"));
			mainLayout = loader.load();
			Scene scene = new Scene(mainLayout);
			scene.getStylesheets().add(getClass().getResource("/view/library.css").toExternalForm());
			// primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
