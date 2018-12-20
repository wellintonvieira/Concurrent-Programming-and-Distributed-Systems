package main;

import java.util.Scanner;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);
		int opt = 0, port = 5000;
		String host = "192.168.1.105";

		do {
			System.out.println("1 - Server");
			System.out.println("2 - Client");
			System.out.println("3 - Exit");
			System.out.println("Choose: ");
			opt = scanner.nextInt();

			if (opt == 1) {
				Server server = new Server(port);
				server.start();
				Thread thread = new Thread(server);
				thread.start();
			} else if (opt == 2) {
				Client client = new Client(host, port, scanner);
				Thread thread = new Thread(client);
				thread.start();
			} else if (opt == 3) {
				System.out.println("Bye :)");
				scanner.close();
				System.exit(0);
			} else {
				System.err.println("Invalid option!!!");
			}
		} while (opt != 3);
	}
}
