package ex01_Socket._01_Eight_Basic_Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class client {
	int portNo;
	String ip;
	InputStream is = null;
	OutputStream os = null;

	public client(String ip, int portNo) {
		super();
		this.portNo = portNo;
		this.ip = ip;
		System.out.println("[Client] 客戶端物件已建立");
	}

	public void connect() {
		try {
			Socket sock = new Socket(ip, portNo);
			System.out.println("[Client] 連線成功 目的端:" + ip + ":" + portNo);
			is = sock.getInputStream();
			os = sock.getOutputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		client c01 = new client("localhost", 59487);
		c01.connect();
// ---<Byte>--------------------------------------
		 int n = c01.receiveByte();System.out.println("   [main]讀取到的資料:"+(byte)n);
// ---<Int>--------------------------------------
		 int n1 = c01.receiveInt();
		 int n2 =c01.receiveInt();System.out.println("   [main]讀取到的資料:"+n1+":"+n2);
		 int sum = n1+n2;
		 c01.sendInt(sum);
//---<Double>--------------------------------------
		 double d = c01.receiveDouble();System.out.println("   [main]讀取到的資料:"+d);
		// ---<Object>--------------------------------------
		Object o = c01.receiveObject();
		System.out.println("  [main]讀取到的資料:" + o);

	}

	public int receiveByte() {
		int b = 0;
		try {
			b = is.read();
			System.out.println("[Client]receiveByte接收到一個位元組的資料: b=" + b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;

	}

	public int receiveInt() {
		int b = 0;
		try {
			DataInputStream dis = new DataInputStream(is);
			b = dis.readInt();
			System.out.println("[Client]receiveInt接收到一個位元組的資料: b=" + b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;

	}

	public double receiveDouble() {
		double b = 0;
		try {
			DataInputStream dis = new DataInputStream(is);
			b = dis.readDouble();
			System.out.println("[Client]receiveDouble接收到一個位元組的資料: b=" + b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;

	}

	public Object receiveObject() {
		Object o = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			o = ois.readObject();
			System.out.println("[Client]receiveObject接收到一個位元組的資料: b=" + o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;

	}

	public void sendInt(int b) {
		try {
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeInt(b);
			System.out.println("[Client]Client 傳送一個位元組的資料: " + b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
