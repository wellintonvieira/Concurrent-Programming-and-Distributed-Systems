package ricartagrawala;

public class Main {

	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		
		Server server = new Server(PORT);
		Thread thread = new Thread(server);
		thread.start();
	}
}
