package byzantinegenerals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Protocol {
	
	public static void send(Socket socket, String message) {
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println(message);
			printWriter.flush();
		} catch (IOException e) {}
	}
	
	public static String receive(Socket socket) {
		String response = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			response = bufferedReader.readLine();
		} catch (IOException e) {}
		return response;
	}
	
	public static void print(String message) {
		System.out.println(message);
	}
	
	public static String getIP() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
}
