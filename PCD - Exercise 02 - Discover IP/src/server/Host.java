package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Host implements Runnable {

	private String IP;
	private int size;
	private Socket client = null;
	private BufferedReader bufferedReader = null;
	private ArrayList<String> hosts = null;
	private ArrayList<String> hostsOn = null;

	public Host() {
		this.IP = "192.168.1.";
		this.size = 254;
		hosts = new ArrayList<String>();
		hostsOn = new ArrayList<String>();
		addHosts();
	}

	public void addHosts() {
		for (int i = 0; i < this.size; i++) {
			hosts.add(IP + i);
		}
	}

	public void addHostsOn(String host, String hostName) {
		if (!hostsOn.contains(host)) {
			hostsOn.add(host);
			System.out.println(host + " = " + hostName);
		}
	}

	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < this.size; i++) {
				String host = hosts.get(i);
				try {
					if (InetAddress.getByName(host).isReachable(10)) {
						client = new Socket(host, 5000);
						bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
						String hostName = bufferedReader.readLine();
						addHostsOn(host, hostName);
						bufferedReader.close();
						client.close();
					} else {
					}
				} catch (IOException ex) {
				}
			}
		}
	}
}
