import java.net.*;
import java.io.*;

public class Receiver {
	int port;
	ServerSocket dSocket;
	Socket serializerSocket;
	DataInputStream fromSerializer;

	public Receiver(int port) {
		this.port = port;

		this.createSocketConnection();
	}

	private void createSocketConnection() {
		try {
			this.dSocket = new ServerSocket(this.port);
			this.serializerSocket = dSocket.accept();
			this.fromSerializer = new DataInputStream(serializerSocket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error setting up sockets.");
		}
	}

	private String readFromClient() {
		try {
			return fromSerializer.readUTF();			
		} catch (IOException e) {
			System.out.println("Socket has closed before trying to read.");
			return "EXIT END";
		}
	}


	public static void main(String[] args) {
		Receiver des = new Receiver(6666);
		int amount = Integer.parseInt(des.readFromClient());


		Visualizer v = new Visualizer();
		for (int i = 0; i < amount; i++) {
			String readObject = des.readFromClient();
			Object deserialized = Deserializer.deserialize(readObject);
			v.inspect(deserialized);
		}

	}
}