package util;

import javafx.scene.control.Alert;

public class AlertUtil {

	public static void show(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
	}
}
