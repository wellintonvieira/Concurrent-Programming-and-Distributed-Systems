package main;

import model.Client;
import model.Server;

public class Main {

	private static int port = 5000;
	private static String host = "10.153.13.69";

	public static void main(String[] args) {

		Server server = new Server(port);
		Thread thread = new Thread(server);
		thread.start();

		Client client = new Client(host, port);
		client.whatTimeIsIt();
	}
}
