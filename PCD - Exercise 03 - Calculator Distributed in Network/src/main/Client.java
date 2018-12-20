package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import json.JSONObject;

public class Client implements Runnable{
	
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private JSONObject jsonObject;
	private Socket socket;
	Scanner scanner;
	
	public Client(String host, int port, Scanner scanner) {
		
		this.scanner = scanner;
		
		try {
			socket = new Socket(host, port);
			jsonObject = new JSONObject();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		while(true) {
		
			double param1 = 0.0;
			double param2 = 0.0;
			
			try {
				System.out.print("Param 1: ");
				param1 = scanner.nextDouble();
				
				System.out.print("Param 2: ");
				param2 = scanner.nextDouble();
				
			}catch (InputMismatchException e) {
				System.exit(0);
			}
			
			jsonObject.put("parametro1", param1);
			jsonObject.put("parametro2", param2);
			
			try {
				
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				printWriter.println(jsonObject.toString());
				printWriter.flush();
				
				String messsage = bufferedReader.readLine();
				jsonObject = new JSONObject(messsage);
				
				double result = jsonObject.getDouble("resultado");
				System.out.println("Resultado: "+result);
				
				scanner.close();
				printWriter.close();
				bufferedReader.close();
				socket.close();
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}		
		}
	}
}
