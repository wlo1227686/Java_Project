package ex01_Socket._02_Mulit_Files;

import java.io.Serializable;

public class FileData implements Serializable {
	String filename;
	long size;

	public FileData(long size, String filename) {
		super();
		this.filename = filename;
		this.size = size;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
