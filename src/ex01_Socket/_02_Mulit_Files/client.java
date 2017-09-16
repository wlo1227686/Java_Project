package ex01_Socket._02_Mulit_Files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class client {
	String dir;
	int portNo;
	String ip;
	InputStream is = null;
	OutputStream os = null;

	public client(String ip, int portNo, String dir) {
		super();
		this.portNo = portNo;
		this.ip = ip;
		this.dir = dir;
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

	public void getFile(FileData[] fd){
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			DataInputStream dis = new DataInputStream(is);
			oos.writeObject(fd);
			for(int n=0; n < fd.length; n++){
				long size = dis.readLong();  // 總共要讀取的位元組數
//				System.out.println("---------------------------------------------------------------------------");
//				System.out.println("Client: n=" + n +  ", file.size()=" + size + ", file=" + fd[n].getFilename() + ", 按下Enter繼續");
				
				if (size >= 0){
					File file = new File(dir, fd[n].getFilename());
					FileOutputStream fos = new FileOutputStream(file, true);
					byte[] b = new byte[81920];
					int len = 0;
					long total = 0 ; 
					while (true) {
						len = dis.read(b, 0,  (int)Math.min(b.length, size));   			// 由網路讀Server送來的資料
//                      						
//						System.out.println("Client: 讀到位元組數=" + len  + ", size=" 
//						        + size + ", (int)Math.min(b.length, size)=" + (int)Math.min(b.length, size) );
						fos.write(b, 0, len);   	// 寫至檔案內
						size -= len;   				// 扣掉本次讀取的位元組數
						total += len;				// 此敘述觀察用
						if (size <= 0)            	// 如果尚未讀取的位元組數小於或等於0
							break;
					}
					fos.close();
					System.out.println("Client: " + file.getName() + ", 接收的位元組數:" + total + ", size=" + size);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileData[]  filenameToArray(String[] fa){
		FileData[] fda = new FileData[fa.length];
		for(int n = 0 ;  n < fa.length ; n++ ){
			File file = new File(dir, fa[n]);
			fda[n] = new FileData(file.length(), file.getName());
		}
		return fda;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		client c01 = new client("localhost", 59487, "ex01_Socket\\client");
		c01.connect();
		String[] files = { "CH01.zip","CH02.zip","CH03.zip","CH04.zip","CH05.zip"};

		FileData[] fd = c01.filenameToArray(files);
		c01.getFile(fd);
	}

}
