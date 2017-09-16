package ex01_Socket._02_Mulit_Files;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	int portNo;
	InputStream is = null;
	OutputStream os = null;
	File repository = null;

	public server(int portNo, File repository) {
		this.portNo = portNo;
		this.repository = repository;
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
				sendFiles();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("[StanBy]Error " + e.getMessage());
		}

	}

	public void sendFiles() {

		try (ObjectInputStream ois = new ObjectInputStream(is); // Input Socket
				DataOutputStream dos = new DataOutputStream(os);// Output Socket
		) {
			FileData[] fds = (FileData[]) ois.readObject(); // fds:
															// Client端要下載的所有檔案
			for (int n = 0; n < fds.length; n++) {
				
				FileData fd = fds[n];
				String filename=fd.getFilename();
				File file = new File(repository, filename);
				long offset = fd.getSize();
				long size;
				if (file.exists()) {
					size = file.length() - offset;
				} else {
					size = -1;
				}
				dos.writeLong(size);
				System.out.println("[Server] (來源)檔案大小:" + size + " 檔案名稱:"+filename);

				if (size >= 0) {
					long total = 0;
					try (
							FileInputStream fis = new FileInputStream(file);
							) {
						fis.skip(offset);
						int len = 0;
						byte[] b = new byte[8192];
						while (size > 0 && (len = fis.read(b, 0, (int) Math.min(b.length, size))) != -1) {
							dos.write(b, 0, len);
							total += len;
							size -= len;
						}
					}
					System.out.println("(送出)檔案大小:" + total);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String Server_folder = "ex01_Socket\\server";
		server s1 = new server(59487, new File(Server_folder));
		s1.standBy();
	}
}
