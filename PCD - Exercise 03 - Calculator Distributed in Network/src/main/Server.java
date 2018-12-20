package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import json.JSONObject;

public class Server implements Runnable {

	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	private JSONObject jsonObject;
	private ServerSocket serverSocket;
	private Socket socket;
	private int port;

	public Server(int port) {
		this.port = port;
	}
	
	public void start() {
		try {
			System.out.println("Server run in port "+this.port);
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("Waiting client...");
				socket = serverSocket.accept();
				System.out.println("Client "+socket.getInetAddress().getHostAddress()+" connected!");
				
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = bufferedReader.readLine();
				
				jsonObject = new JSONObject(message);
				
				double value1 = jsonObject.getDouble("parametro" + 1);
				double value2 = jsonObject.getDouble("parametro" + 2);
				double result = 0.0;
				
				if(value2 != 0) {
					result = value1 / value2;	
				}

				System.out.println("Param 1: " + value1);
				System.out.println("Param 2: " + value2);
				System.out.println("Result: " + result);
				
				jsonObject = new JSONObject();
				jsonObject.put("resultado", result);
				
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				printWriter.println(jsonObject.toString());
				
				socket.close();
				bufferedReader.close();
				printWriter.close();
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}	
		}
	}
}
