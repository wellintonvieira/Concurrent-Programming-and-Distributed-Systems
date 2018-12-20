package main;

import controller.ControllerMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Receiver;
import resource.Strings;
import util.AlertUtil;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

	private static ControllerMain controllerMain;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/FXMLMain.fxml"));

			AnchorPane anchorPane = fxmlLoader.load();
			controllerMain = fxmlLoader.getController();

			primaryStage.setScene(new Scene(anchorPane, 600, 400));
			primaryStage.setTitle(Strings.app_name);
			primaryStage.setResizable(false);
			primaryStage.show();
			startReceiver();

		} catch (Exception ex) {
			AlertUtil.show(Alert.AlertType.ERROR, Strings.app_error_start, ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void startReceiver() {
		Receiver receiver = new Receiver(controllerMain);
		Thread thread = new Thread(receiver);
		thread.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
