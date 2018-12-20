package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private Socket socket;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server running in port " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("Waiting clients in port " + serverSocket.getLocalPort());
			socket = serverSocket.accept();
			System.out.println("New client connected: " + socket.getRemoteSocketAddress());

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

			long t0 = dataInputStream.readLong();

			System.out.println("T0: "+t0);
			long t1 = System.currentTimeMillis();
			long t2 = System.currentTimeMillis();
			
			System.out.println("T1: "+t1);
			System.out.println("T2: "+t2);

			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.writeLong(t0);
			dataOutputStream.writeLong(t1);
			dataOutputStream.writeLong(t2);
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
