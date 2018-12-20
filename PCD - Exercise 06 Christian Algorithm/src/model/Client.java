package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	private Socket socket;

	public Client(String host, int port) {
		try {
			socket = new Socket(host, port);
			System.out.println("Client connect in address server: " + socket.getInetAddress().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void whatTimeIsIt() {

		try {
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			long t0 = System.currentTimeMillis();
			System.out.println("T0: "+t0);
			dataOutputStream.writeLong(t0);

			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);

			long t1 = dataInputStream.readLong();
			long t2 = dataInputStream.readLong();
			long t3 = System.currentTimeMillis();

			System.out.println("T1: "+t1);
			System.out.println("T2: "+t2);
			System.out.println("T3: "+t3);
			
			long cristianTime = ((t1 - t0) + (t2 - t3)) / 2;
			System.out.println("Hour: " + cristianTime);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
