package main;

import server.Server;

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		Thread thread = new Thread(server);
		thread.start();
	}
}
