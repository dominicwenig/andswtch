package sta.andswtch.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.util.Log;

public class Sender {
	private static final String TAG = Sender.class.getName();
	
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
			throws IOException, SocketException {

		InetAddress destAddress = InetAddress.getByName(destHost);
		Log.d(TAG, "the destination address is: " + destAddress.getHostAddress());

		DatagramSocket sendSocket = new DatagramSocket();

		DatagramPacket packet = new DatagramPacket(data, data.length,
				destAddress, destPort);

		sendSocket.send(packet);

		sendSocket.close();
	}
}
