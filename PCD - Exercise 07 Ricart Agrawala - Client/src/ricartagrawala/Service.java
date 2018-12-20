package ricartagrawala;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Service implements Runnable {

	private ServerSocket serverSocket;
	private Process process;
	private Socket socket;
	
	public Service(int port, Process process) {
		this.process = process;
		try {
			serverSocket = new ServerSocket(port);
			Protocol.print("Proccess id: " + port);
		} catch (IOException e) {}
	}

	@Override
	public void run() {
		while (true) {
			try {
				socket = serverSocket.accept();
				String request = Protocol.receive(socket);

				if (request.contains("write")) {
					if(process.getLocked()) {
						Protocol.send(socket, "nok");
					}else {
						Protocol.send(socket, "ok");
					}
				}
			} catch (IOException e) {}
		}
	}
}
