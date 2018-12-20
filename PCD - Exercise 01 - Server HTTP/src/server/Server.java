package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Server implements Runnable {

	private int port;
	private ServerSocket server;
	private Socket client;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;

	public Server() {
		this.port = 8080;
	}

	public void start() {
		try {
			server = new ServerSocket(port);
			System.out.println("Server running in port " + port);
			System.out.println("Waiting clients...");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public String sendMessage(String message) {
		String html = "HTTP/1.1 200 OK\r\n\r\n"
				+ "<html lang="+"pt-br"+">"
				+ "<head>"
				+ "<title>"
				+ "Tarefa 1"
				+ "</title>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<head>"
				+ "<body>"
				+ "<h1>"
				+ message
				+ "</h1>"
				+ "</body>"
				+ "</html>";
		return html;
	}

	@Override
	public void run() {
		while (true) {
			try {
				client = server.accept();
				System.out.println("New client connection: " + client.getInetAddress().getHostAddress());

				bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String message = bufferedReader.readLine();
				System.out.println(message);

				if (message.contains("/date")) {
					printWriter = new PrintWriter(client.getOutputStream(), true);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy	");
					Date date = new Date();
					printWriter.println(sendMessage("Data: "+ simpleDateFormat.format(date)));
				} else {
					Random random = new Random();
					printWriter = new PrintWriter(client.getOutputStream(), true);
					String number = "" + random.nextInt(1000);
					printWriter.println(sendMessage("Número Aleatório: "+ number));
				}
				printWriter.flush();
				bufferedReader.close();
				printWriter.close();
				client.close();

			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
