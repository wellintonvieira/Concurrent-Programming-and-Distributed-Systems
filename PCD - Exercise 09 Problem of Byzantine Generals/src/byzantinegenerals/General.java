package byzantinegenerals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class General implements Runnable {

	private int portGeneral;
	private boolean commander;
	private boolean byzantine;
	private Random random;
	private Socket socket;
	private ArrayList<String> messages;
	private ArrayList<Integer> ports;
	private ServerSocket serverSocket;

	public General(int portGeneral, boolean commander, boolean byzantine, ArrayList<Integer> ports) {
		this.ports = ports;
		this.byzantine = byzantine;
		this.commander = commander;
		this.portGeneral = portGeneral;
		random = new Random();
		messages = new ArrayList<String>();

		try {
			serverSocket = new ServerSocket(portGeneral);
			Protocol.print("General is running in port " + portGeneral);
		} catch (IOException e) {
			Protocol.print(e.getMessage());
		}
	}

	@Override
	public void run() {

		if (commander) {
			// Passo 1
			String message = 1 + ";" + getOrder() + ";" + portGeneral + ";" + byzantine;
			sendToAllGenerals(message);
		}

		int count = 0;
		int number_generals = ports.size() - 2;

		while (true) {
			try {
				socket = serverSocket.accept();
				String receive = Protocol.receive(socket);
				String[] receive_split = receive.split(";");
				int step = Integer.parseInt(receive_split[0]);
				String message = receive_split[1];
				int port = Integer.parseInt(receive_split[2]);
				boolean isByzantine = Boolean.parseBoolean(receive_split[3]);

				Protocol.print("General " + portGeneral + " receice [" + message + "] of general " + port + " at step "
						+ step + " is byzantine " + isByzantine);

				if (step == 1) {
					if (byzantine) {
						// Passo 2
						String m = null;
						for (Integer p : ports) {
							m = 2 + ";" + getOrder() + ";" + portGeneral + ";" + byzantine;
							if (p != portGeneral) {
								send(p, m);
							}
						}
					} else {
						// Passo 2
						message = 2 + ";" + message + ";" + portGeneral + ";" + byzantine;
						sendToAllGenerals(message);
					}
				} else if (step == 2) {
					// Passo 3
					count++;
					messages.add(message);
					if (count == number_generals) {
						if (checkMessages(number_generals, message)) {
							message = 3 + ";OK;" + portGeneral + ";" + byzantine;
						} else {
							message = 3 + ";NOK;" + portGeneral + ";" + byzantine;
						}
						sendToAllGenerals(message);
					}
				} else if (step == 3) {
					Protocol.print("General " + portGeneral + " receice " + message);
				}

			} catch (IOException e) {
				Protocol.print(e.getMessage());
			}
		}
	}

	private boolean checkMessages(int number_generals, String message) {

		int check = 0;

		for (String m : messages) {
			if (m.contains(message)) {
				check++;
			}
		}

		if (check == number_generals) {
			return true;
		}

		return false;
	}

	private String getOrder() {
		int number = random.nextInt(2);
		if (number == 0) {
			return Script.ATTACK.toString();
		}
		return Script.WITHDRAW.toString();
	}

	private void sendToAllGenerals(String message) {
		for (Integer port : ports) {
			if (port != portGeneral) {
				send(port, message);
			}
		}
	}

	private void send(int port, String message) {
		try {
			Socket socket = new Socket(Protocol.getIP(), port);
			Protocol.send(socket, message);
			Protocol.print("General " + portGeneral + " send [" + message + "] to general " + port);
		} catch (IOException e) {
			Protocol.print(e.getMessage());
		}
	}
}
