package main;

import java.io.IOException;

import server.Host;
import server.Server;

public class Main {

	public static void main(String args[]) throws IOException {

		Server server = new Server();
		Thread threadServer = new Thread(server);
		threadServer.start();

		Host host = new Host();
		Thread threadHost = new Thread(host);
		threadHost.start();

	}
}
