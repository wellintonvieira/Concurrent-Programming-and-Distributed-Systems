package ricartagrawala;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server implements Runnable {

	private ArrayList<String> resource;
	private ServerSocket serverSocket;
	private Socket socket;

	public Server(int port) {
		resource = new ArrayList<String>();
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is running in port " + port);
			System.out.println("Waiting clients...");
		} catch (IOException e) {}
	}

	@Override
	public void run() {
		while (true) {
			try {
				socket = serverSocket.accept();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String request = bufferedReader.readLine();
				resource.add(request);
				
				int position = resource.size() - 1;
				Date date = new Date();
				System.out.println(resource.get(position) + " is writing in resource - " + date+"\n");
				
				bufferedReader.close();
				socket.close();
				
			} catch (IOException e) {}
		}
	}
}
