package me.drewhoener.chatserver.packets;

import me.drewhoener.chatserver.network.api.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IPacket0RequestLogin implements Packet {

	public String username;

	public IPacket0RequestLogin() {
	}

	public IPacket0RequestLogin(String username) {
		this.username = username;
	}

	@Override
	public void read(DataInputStream inputStream) throws IOException {
		this.username = inputStream.readUTF();
	}

	@Override
	public void write(DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF(this.username);
	}

	@Override
	public int getID() {
		return 0;
	}

}
