package com.zafar;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

public class HashTest {

	public static void main(String[] args) throws IOException {
		InputStream fi = new BufferedInputStream(new FileInputStream("/tmp/shared/Untitled Folder/1.mp4"));
		byte[] buf = new byte[1024*64];
		int gByte;
		CRC32 gCRC = new CRC32();
		while ((gByte = fi.read(buf)) > 0) {
		    gCRC.update(buf, 0, gByte);
		}
		fi.close();
		System.out.println(gCRC.getValue());
	}
}