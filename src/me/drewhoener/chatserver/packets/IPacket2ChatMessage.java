package me.drewhoener.chatserver.packets;

import me.drewhoener.chatserver.network.api.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IPacket2ChatMessage implements Packet {

	public String chatMessage = "";

	public IPacket2ChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}

	public IPacket2ChatMessage() {
	}

	@Override
	public void read(DataInputStream inputStream) throws IOException {
		this.chatMessage = inputStream.readUTF();
	}

	@Override
	public void write(DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF(this.chatMessage);
	}

	@Override
	public int getID() {
		return 2;
	}

}
