package me.drewhoener.chatserver;

import me.drewhoener.chatserver.network.ClientHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerChat extends Thread {

	public final ServerSocket serverSocket;

	public final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

	public ServerChat(InetSocketAddress bindAddress) throws IOException {

		this.serverSocket = new ServerSocket();

		this.serverSocket.bind(bindAddress);

		this.start();

	}

	public boolean isUsernameTaken(String username) {

		for (ClientHandler client : this.clients) {

			if (client.username != null && client.username.equalsIgnoreCase(username)) {

				return true;

			}

		}

		return false;

	}

	@Override
	public void run() {

		while (!this.serverSocket.isClosed()) {

			try {

				this.clients.add(new ClientHandler(this, this.serverSocket.accept()));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws IOException {

		if (args.length <= 0) {

			System.out.println("Usage: java -jar <Jar Name> -<port number>");
			System.exit(0);

		} else {

			int port = Integer.parseInt(args[0]);

			ServerChat serverChat = new ServerChat(new InetSocketAddress("0.0.0.0", port));

			boolean isStopping = false;

			Scanner scanner = new Scanner(System.in);

			while (!isStopping && scanner.hasNextLine()) {

				switch (scanner.nextLine().toLowerCase()) {

					case "exit":
					case "stop":
						System.out.println("Stopping...");
						isStopping = true;
						break;
					default:
						System.out.println("Unknown command.");
						break;

				}

			}

			scanner.close();
			serverChat.serverSocket.close();

		}

	}

}
