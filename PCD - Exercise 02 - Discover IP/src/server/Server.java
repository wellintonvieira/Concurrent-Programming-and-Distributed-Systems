package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private PrintWriter printWriter = null;
	private ServerSocket server = null;
	private Socket client = null;

	public Server() throws IOException {
		server = new ServerSocket(5000);
		System.out.println("Servidor: " + InetAddress.getLocalHost().getHostAddress() + " rodando na porta 5000");
		System.out.println("Esperando clientes...");
	}

	@Override
	public void run() {
		while (true) {
			try {
				client = server.accept();
				printWriter = new PrintWriter(client.getOutputStream(), true);
				printWriter.print("Wellinton");
				printWriter.flush();
				printWriter.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Erro: " + ex);
			}
		}
	}
}
