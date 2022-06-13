import java.util.ArrayList;

import java.net.*;
import java.io.*;

public class Sender {
	private String desHostName;
	private int desPort;

	private Socket desSocket;
	private DataOutputStream toDeserializer;


	public Sender(String hostname, int port) {
		this.desHostName = hostname;
		this.desPort = port;

		this.connectToServer();
	}

	private void connectToServer() {
		try {
			this.desSocket = new Socket(this.desHostName, this.desPort);
			this.toDeserializer = new DataOutputStream(desSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Error connecting to Deserializer");
			System.exit(-1);
		}
	}

	private void sendMessage(String message) {
		try {
			this.toDeserializer.writeUTF(message);
		} catch (IOException e) {
			System.out.println("Error sending to Deserializer");
		}
	}

	private void closeSocket() {
		try {
			this.desSocket.close();
		} catch (IOException e) {
			System.out.println("Error closing connection to Deserializer");
		}
	}


	public static void main(String[] args) {
		ObjectCreator creator = new ObjectCreator();

		ArrayList<Object> myObjects = new ArrayList<Object>();
		Object o = creator.createObject();
		while (o != null) {
			myObjects.add(o);
			o = creator.createObject();
		}

		ArrayList<String> serializedObjects = new ArrayList<String>();
		for (int i = 0; i < myObjects.size(); i++) {
			serializedObjects.add(Serializer.serializeObject(myObjects.get(i)));
		}


		Sender s = new Sender("localhost", 6666);
		s.sendMessage(String.valueOf(serializedObjects.size()));

		for (int i = 0; i < myObjects.size(); i++) {
			s.sendMessage(serializedObjects.get(i));
		}

		s.closeSocket();
	}
}