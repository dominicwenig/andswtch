package sta.andswtch.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
	/**
	 * sends a datagram packet containing the command as String to the
	 * destination host on destination port
	 * 
	 * @param destHost
	 * @param destPort
	 * @param command
	 * @throws IOException
	 */
	public void send(String destHost, int destPort, byte[] data)
			throws IOException {

		//byte[] data = command.getBytes();

		InetAddress destAddress = InetAddress.getByName(destHost);

		DatagramSocket sendSocket = new DatagramSocket();

		DatagramPacket packet = new DatagramPacket(data, data.length,
				destAddress, destPort);

		sendSocket.send(packet);

		sendSocket.close();
	}
}
