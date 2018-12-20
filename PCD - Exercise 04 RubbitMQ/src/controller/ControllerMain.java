package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Emitter;
import resource.Strings;
import util.AlertUtil;

public class ControllerMain {

	@FXML
	private ListView<String> listView;

	@FXML
	private TextArea textAreaSend;

	@FXML
	private TextArea textAreaReceive;

	@FXML
	private CheckBox checkBox;

	private ObservableList<String> queuesList;
	private boolean check;

	@FXML
	void initialize() {
		listView.getItems().addAll(Strings.getQueues());
	}

	@FXML
	void buttonClean(ActionEvent event) {
		cleanFields();
	}

	@FXML
	void buttonSend(ActionEvent event) {
		if (textAreaSend.getText().trim().isEmpty()) {
			AlertUtil.show(Alert.AlertType.ERROR, Strings.app_name, Strings.app_error_empty);
		} else {
			sendMessage();
		}
	}

	@FXML
	void textAreaSend(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			if (textAreaSend.getText().trim().isEmpty()) {
				AlertUtil.show(Alert.AlertType.ERROR, Strings.app_name, Strings.app_error_empty);
			} else {
				sendMessage();
			}
		}
	}

	@FXML
	void checkBox(ActionEvent event) {
		if (check) {
			check = false;
		} else {
			check = true;
		}
	}

	private void sendMessage() {

		queuesList = listView.getSelectionModel().getSelectedItems();
		String queueName = "", message = "";
		boolean ok = false;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		for (String queue : queuesList) {
			queueName += queue;
		}

		if (check) {
			message = simpleDateFormat.format(date) + "\n" + Strings.app_message_emitter + " [" + Strings.app_topic
					+ "]: " + textAreaSend.getText() + "\n";
			ok = true;
			queueName = Strings.app_topic;
		} else if (queueName.isEmpty() && check == false) {
			AlertUtil.show(Alert.AlertType.ERROR, Strings.app_name, Strings.app_error_queue);
		} else {
			message = simpleDateFormat.format(date) + "\n" + Strings.app_message_emitter + " [" + queueName + "]: "
					+ textAreaSend.getText() + "\n";
			ok = true;
		}

		if (ok) {
			Emitter emitter = new Emitter(queueName, "[" + Strings.app_queue + "]: " + textAreaSend.getText(), check);
			Thread thread = new Thread(emitter);
			thread.start();

			textAreaReceive.appendText(message + "\n");
			textAreaSend.setText("");
			textAreaSend.requestFocus();
		}
	}

	public void printMessage(String receiver) {

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		String message = simpleDateFormat.format(date) + "\n" + Strings.app_message_receiver + " " + receiver + "\n";
		textAreaReceive.appendText(message + "\n");
	}

	private void cleanFields() {
		textAreaSend.setText("");
		textAreaReceive.setText("");
		check = false;
		checkBox.setSelected(false);
	}
}
