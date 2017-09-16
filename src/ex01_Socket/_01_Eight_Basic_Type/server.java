package ex01_Socket._01_Eight_Basic_Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	int portNo;
	InputStream is = null;
	OutputStream os = null;

	public server(int portNo) {
		this.portNo = portNo;
		System.out.println("[Server] Server端物件已建立");
	}

	private void standBy() {
		try {
			ServerSocket ss = new ServerSocket(portNo);
			while (true) {
				System.out.println("[Server] Server待命中...");
				Socket sc = ss.accept();
				System.out.println("[Server] Server已接受遠方客戶端的連線要求");
				is = sc.getInputStream();
				os = sc.getOutputStream();
				
				sendByte(127);
				sendInt(55588222);
				sendInt(12345678);			
				int n = receiveInt();
				System.out.println(n);
				sendDouble(555.88222);
				sendObject("yeee");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("[StanBy]Error " + e.getMessage());
		}

	}

	public static void main(String[] args) {
		server s1 = new server(59487);
		s1.standBy();

	}
	
	
	
	
	public void sendByte(int b) {

		try {
			os.write(b);
			System.out.println("[Server]Server 傳送一個位元組的資料: " + b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendInt(int b){
		try {
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeInt(b);
			System.out.println("[Server]Server 傳送一個位元組的資料: " + b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendDouble(double b){
		try {
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeDouble(b);
			System.out.println("[Server]Server 傳送一個位元組的資料: " + b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendObject(Object o){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(o);
			System.out.println("[Server]Server 傳送一個位元組的資料: " + o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int receiveInt() {
		int b = 0;
		try {
			DataInputStream dis = new DataInputStream(is);
			b = dis.readInt();
			System.out.println("[Server]接收到一個位元組的資料: b=" + b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;

	}
}
