package ricartagrawala;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static final int PORT = 5001;
	private static Scanner scanner;

	public static void main(String[] args) throws UnknownHostException {

		scanner = new Scanner(System.in);
		int number_ports = 0;
		int port = 0;

		try {
			System.out.print("Enter the number of ports: ");
			number_ports = scanner.nextInt();

			System.out.println("\n--- Choose a port ---\n");

			for (int i = 0; i < number_ports; i++) {
				int p = PORT + i;
				System.out.println(i + 1 + " - " + p);
			}

			int old_port = 0;

			while (true) {
				System.out.print("\nPort: ");
				old_port = scanner.nextInt();

				if (old_port > 0 && old_port <= number_ports) {
					break;
				} else {
					System.out.println("Invalid argument!");
				}
			}

			port = PORT + old_port;

		} catch (InputMismatchException e) {
			System.out.println("Invalid argument!");
			return;
		}

		scanner.nextLine();
		String name = null;

		while (true) {
			System.out.print("\nEnter the your name: ");
			name = scanner.nextLine();

			if (name.isEmpty()) {
				System.out.println("Invalid argument!");
			} else {
				break;
			}
		}

		System.out.println("\n");

		ArrayList<Integer> ports = new ArrayList<Integer>();

		for (int i = 0; i < number_ports; i++) {
			int p = PORT + i;
			if (port != p) {
				ports.add(p);
			}
		}

		String host = InetAddress.getLocalHost().getHostAddress();
		Process process = new Process(ports, host, name);
		Service service = new Service(port, process);

		Thread threadService = new Thread(service);
		Thread threadProcess = new Thread(process);

		threadService.start();
		threadProcess.start();
	}
}
