package model;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javafx.scene.control.Alert;
import resource.Strings;
import util.AlertUtil;

public class Emitter implements Runnable {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Channel channelQueue;
	private Channel channelTopic;
	private String queue;
	private String message;
	private boolean isTopic;

	public Emitter(String queue, String message, boolean topic) {
		this.queue = queue;
		this.message = message;
		this.isTopic = topic;
	}

	@Override
	public void run() {
		try {
			connectionFactory = new ConnectionFactory();
			connectionFactory.setHost(Strings.app_host);
			connectionFactory.setVirtualHost(Strings.app_vhost);
			connectionFactory.setUsername(Strings.app_username);
			connectionFactory.setPassword(Strings.app_password);

			connection = connectionFactory.newConnection();
			channelQueue = connection.createChannel();
			channelTopic = connection.createChannel();

			if (isTopic) {
				channelTopic.exchangeDeclare(queue, BuiltinExchangeType.FANOUT);
				channelTopic.basicPublish(queue, "", null, message.getBytes("UTF-8"));
				System.out.println(queue + " : " + message);
			} else {
				channelQueue.queueDeclare(queue, false, false, false, null);
				channelQueue.basicPublish("", queue, null, message.getBytes("UTF-8"));
			}

			channelTopic.close();
			channelQueue.close();
			connection.close();

		} catch (IOException | TimeoutException ex) {
			AlertUtil.show(Alert.AlertType.ERROR, Strings.app_error_send, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
