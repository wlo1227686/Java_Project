package ex02_BroadCast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		Join_Group("239.254.0.0", "59487");
		Do_BroadCast("255.255.255.255", "27798", "4000", "Hello_Word");
	}

	public static void Join_Group(String Group_IP, String Port) {
		try (MulticastSocket mySocket = new MulticastSocket(Integer.parseInt(Port))) {
			mySocket.joinGroup(InetAddress.getByName(Group_IP));
		} catch (Exception e) {
			System.out.println("[IGMP]Error Message:" + e.getMessage());
		}
	}

	public static void Do_BroadCast(String IP, String Port, String DelayTime, String Data) {
		byte[] Buf01 = Data.getBytes();
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			while (true) {
				serverSocket.send(
						new DatagramPacket(Buf01, Buf01.length, InetAddress.getByName(IP), Integer.parseInt(Port)));
				System.out.println("[UDP] IP=" + IP + " Port=" + Port + " Time="
						+ new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()) + " Size=" + Buf01.length
				// + " Data[Hex]=" + byteArrayToHex(Buf01)
				);
				delay_time(Integer.parseInt(DelayTime));
			}
		} catch (Exception e) {
			System.out.println("[UDP]Error Message:" + e.getMessage());
		}
	}

	public static String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder(a.length * 2);
		for (byte b : a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

	public static void delay_time(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void random_time_p(int upperBound, int lowerBound) {

		int num = (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
		System.out.println("延遲時間:" + num / 1000 + "." + num % 1000 + "秒");
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
