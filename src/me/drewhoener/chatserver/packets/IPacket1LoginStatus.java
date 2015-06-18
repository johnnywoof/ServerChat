package me.drewhoener.chatserver.packets;

import me.drewhoener.chatserver.network.api.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IPacket1LoginStatus implements Packet {

	public boolean loginSuccessful;
	public String reason;

	public IPacket1LoginStatus(boolean loginSuccessful, String reason) {
		this.loginSuccessful = loginSuccessful;
		this.reason = reason;
	}

	public IPacket1LoginStatus() {
	}

	@Override
	public void read(DataInputStream inputStream) throws IOException {
		this.loginSuccessful = inputStream.readBoolean();
		this.reason = inputStream.readUTF();
	}

	@Override
	public void write(DataOutputStream outputStream) throws IOException {
		outputStream.writeBoolean(this.loginSuccessful);
		outputStream.writeUTF(this.reason);
	}

	@Override
	public int getID() {
		return 1;
	}

}
