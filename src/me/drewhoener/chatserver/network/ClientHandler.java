package me.drewhoener.chatserver.network;

import me.drewhoener.chatserver.ServerChat;
import me.drewhoener.chatserver.network.api.PacketReadHandler;
import me.drewhoener.chatserver.network.api.PacketRegistrator;
import me.drewhoener.chatserver.network.api.SocketManager;
import me.drewhoener.chatserver.packets.IPacket0RequestLogin;
import me.drewhoener.chatserver.packets.IPacket1LoginStatus;
import me.drewhoener.chatserver.packets.IPacket2ChatMessage;
import me.drewhoener.chatserver.packets.IPacket3Disconnect;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler {

	private static final PacketRegistrator packetRegistrator = new PacketRegistrator();

	public final UUID instanceUUID = UUID.randomUUID();

	private final SocketManager socketManager;
	private final ServerChat serverChat;

	public String username = null;

	public ClientHandler(ServerChat serverChat, Socket socket) throws IOException {

		this.serverChat = serverChat;
		this.socketManager = new SocketManager(this, packetRegistrator, socket);

	}

	public void disconnect(String reason) {

		this.serverChat.clients.remove(this);
		this.socketManager.disconnect();

		this.socketManager.writeThread.sendPacket(new IPacket3Disconnect(reason));

	}

	@PacketReadHandler
	public void onPacket(IPacket0RequestLogin packet) {

		if (packet.username.isEmpty()) {

			this.socketManager.writeThread.sendPacket(new IPacket1LoginStatus(false, "Invalid username."));

		} else if (serverChat.isUsernameTaken(packet.username)) {

			this.socketManager.writeThread.sendPacket(new IPacket1LoginStatus(false, "Username already taken."));

		} else {

			if (this.username == null) {

				this.username = packet.username;

			} else {

				this.disconnect("Not expecting login packet.");

			}

		}

	}

	@PacketReadHandler
	public void onPacket(IPacket2ChatMessage packet) {

		if (this.username != null) {

			IPacket2ChatMessage packet2ChatMessage = new IPacket2ChatMessage(this.username + ": " + packet.chatMessage);

			for (ClientHandler client : this.serverChat.clients) {

				if (client.username != null) {//Logged in

					client.socketManager.writeThread.sendPacket(packet2ChatMessage);

				}

			}

		} else {

			this.disconnect("Not logged in.");

		}

	}

	@PacketReadHandler
	public void onPacket(IPacket3Disconnect packet) {

		this.serverChat.clients.remove(this);
		this.socketManager.disconnect();

	}

	@Override
	public boolean equals(Object o) {

		return o == this || o instanceof ClientHandler && (((ClientHandler) o).instanceUUID.equals(this.instanceUUID));

	}

	static {

		try {

			packetRegistrator.registerPacket(new IPacket0RequestLogin());
			packetRegistrator.registerPacket(new IPacket2ChatMessage());
			packetRegistrator.registerPacket(new IPacket3Disconnect());

		} catch (NoSuchMethodException e) {

			e.printStackTrace();

		}

	}

}
