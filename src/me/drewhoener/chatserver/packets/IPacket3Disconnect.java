package me.drewhoener.chatserver.packets;

import me.drewhoener.chatserver.network.api.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IPacket3Disconnect implements Packet {

	public String reason;

	public IPacket3Disconnect(String reason) {

		this.reason = reason;

	}

	public IPacket3Disconnect() {
	}

	@Override
	public void read(DataInputStream inputStream) throws IOException {
		this.reason = inputStream.readUTF();
	}

	@Override
	public void write(DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF(this.reason);
	}

	@Override
	public int getID() {
		return 3;
	}
}
