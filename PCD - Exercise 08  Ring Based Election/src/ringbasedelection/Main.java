package ringbasedelection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static Scanner scanner;
	private static Random random;

	public static void main(String[] args) throws UnknownHostException {

		scanner = new Scanner(System.in);
		random = new Random();
		
		int port = 0;
		int next = 0;
		int id = random.nextInt(100);
		boolean participant = false;
		String host = InetAddress.getLocalHost().getHostAddress();

		try {
			System.out.print("Enter the port process: ");
			port = scanner.nextInt();

			System.out.print("Enter the next port process: ");
			next = scanner.nextInt();

			System.out.print("Enter if the process is participant (true or false): ");
			participant = scanner.nextBoolean();

		} catch (InputMismatchException e) {
			Protocol.print("Invalid argument!");
			System.exit(0);
		}

		Process process = new Process(id, port, next, participant, host);
		Thread thread = new Thread(process);
		thread.start();
	}
}
