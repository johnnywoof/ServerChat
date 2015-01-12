package me.drewhoener.chatserver.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IPacket {

	public void readIn(DataInputStream inputStream);

	public void writeOut(DataOutputStream outputStream);

	public int getID();

}
