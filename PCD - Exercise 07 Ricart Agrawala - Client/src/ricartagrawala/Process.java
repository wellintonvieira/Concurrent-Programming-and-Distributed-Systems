package ricartagrawala;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Process implements Runnable {

	private final int SERVER = 5000;

	private ArrayList<Integer> ports;
	private boolean locked;
	private String host;
	private String name;

	public Process(ArrayList<Integer> ports, String host, String name) {
		this.locked = false;
		this.ports = ports;
		this.host = host;
		this.name = name;
	}

	public boolean getLocked() {
		return locked;
	}

	@Override
	public void run() {
		while (true) {

			int qtd_ok = 0;

			for (Integer port : ports) {
				try {
					Socket socket = new Socket(host, port);
					Protocol.send(socket, "write");

					String request = Protocol.receive(socket);

					if (request.contains("nok")) {
						break;
					} else {
						qtd_ok++;
					}
				} catch (IOException e) {}
			}

			if (qtd_ok == ports.size() - 1) {
				try {
					locked = true;
					Socket socket = new Socket(host, SERVER);
					Protocol.send(socket, this.name);
					Protocol.print(this.name + " is using resource !");
					Protocol.sleep();
					locked = false;
				} catch (IOException e) {}
			} else {
				Protocol.print("Resource is locked");
			}
		}
	}
}
