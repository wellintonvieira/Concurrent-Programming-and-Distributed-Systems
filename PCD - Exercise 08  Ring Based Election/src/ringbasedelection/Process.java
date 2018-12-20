package ringbasedelection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Process implements Election, Runnable {

	private int id;
	private int next;
	private int cordinator;
	private boolean participant;

	private String host;
	private Socket socket;
	private ServerSocket serverSocket;

	public Process(int id, int port, int next, boolean participant, String host) {
		this.id = id;
		this.next = next;
		this.host = host;
		this.participant = participant;

		try {
			serverSocket = new ServerSocket(port);
			Protocol.print("Process " + id + " is running in port " + port);
		} catch (IOException e) {
			Protocol.print(e.getMessage());
		}

		if (participant) {
			String message = id + ";" + Script.ELECTION;
			election(message);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				socket = serverSocket.accept();

				String message = Protocol.receive(socket);
				String[] request = message.split(";");
				int next_id = Integer.parseInt(request[0]);

				if (message.contains(Script.ELECTION.toString())) {

					if (id < next_id) {
						election(message);
					} else if (id > next_id && participant == false) {
						message = id + ";" + Script.ELECTION;
						election(message);
					} else if (id == next_id) {
						message = id + ";" + Script.ELECTED;
						election(message);
					}

				} else {

					participant = false;
					cordinator = next_id;

					if (id == cordinator) {
						Protocol.print("Process " + id + " is the elected!");
					} else {
						Protocol.print("The cordinator is the process id " + cordinator);
						election(message);
					}
					break;
				}

			} catch (IOException e) {
				Protocol.print(e.getMessage());
			}
		}
	}

	@Override
	public void election(String message) {
		try {
			Socket socket = new Socket(host, next);
			Protocol.send(socket, message);

			String[] request = message.split(";");
			int next_id = Integer.parseInt(request[0]);

			if (participant) {
				Protocol.print("Process " + id + " is the participant");
			}

			Protocol.print("Process " + id + " sending message to the process " + next_id);

		} catch (IOException e) {
			Protocol.print(e.getMessage());
		}
	}
}
