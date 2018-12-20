package model;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import controller.ControllerMain;
import javafx.scene.control.Alert;
import resource.Strings;
import util.AlertUtil;

public class Receiver implements Runnable {

	private ControllerMain controllerMain;
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Channel channelQueue;
	private Channel channelTopic;
	private Consumer consumerQueue;
	private Consumer consumerTopic;
	
	public Receiver(ControllerMain controllerMain) {
		this.controllerMain = controllerMain;
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
			
			channelQueue.queueDeclare(Strings.app_queue, false, false, false, null);
			channelTopic.exchangeDeclare(Strings.app_topic, BuiltinExchangeType.FANOUT);
			String queueName = channelTopic.queueDeclare().getQueue();
			
			channelTopic.queueBind(queueName, Strings.app_topic, "");
			
			consumerTopic = new DefaultConsumer(channelTopic) {
	            @Override
	            public void handleDelivery(String consumerTag, Envelope envelope,
	                    AMQP.BasicProperties properties, byte[] body) throws IOException {
	                String message = new String(body, "UTF-8");
	                controllerMain.printMessage(message);
	            }
	        };
			
			consumerQueue = new DefaultConsumer(channelQueue) {
	            @Override
	            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	                    throws IOException {
	                String message = new String(body, "UTF-8");
	                controllerMain.printMessage(message);
	            }
	        };
	        
	        channelTopic.basicConsume(queueName, true, consumerTopic);
			channelQueue.basicConsume(Strings.app_queue, true, consumerQueue);
	        
		} catch (IOException | TimeoutException ex) {
			AlertUtil.show(Alert.AlertType.ERROR, Strings.app_error_send, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
