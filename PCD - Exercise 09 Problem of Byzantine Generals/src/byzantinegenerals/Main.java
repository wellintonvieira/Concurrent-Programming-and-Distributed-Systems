package byzantinegenerals;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static final int PORT = 5000;
	private static final int NUMBER_GENERALS = 4;
	private static Scanner scanner;

	public static void main(String[] args) {

		ArrayList<Integer> ports = new ArrayList<Integer>();
		scanner = new Scanner(System.in);

		Protocol.print("Ports");
		for (int i = 0; i < NUMBER_GENERALS; i++) {
			int port = PORT + i;
			ports.add(port);
			Protocol.print(i + " - port " + ports.get(i));
		}

		int portGeneral = 0;
		boolean commander = false;
		boolean byzantine = false;

		try {
			while (true) {
				Protocol.print("Choose a general port: ");
				portGeneral = scanner.nextInt();

				if (portGeneral >= 0 && portGeneral < NUMBER_GENERALS) {
					break;
				} else {
					Protocol.print("Invalid Argument!");
				}
			}

			Protocol.print("Enter if the general is commander (true or false): ");
			commander = scanner.nextBoolean();
			
			Protocol.print("Enter if the general is byzantine (true or false): ");
			byzantine = scanner.nextBoolean();

		} catch (InputMismatchException e) {
			Protocol.print(e.getMessage());
		}

		int port = PORT + portGeneral;
		General general = new General(port, commander, byzantine, ports);
		Thread thread = new Thread(general);
		thread.start();
	}
}
