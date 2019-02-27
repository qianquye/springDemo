package cn.lvyjx.test.oom;

import java.io.IOException;

public class OutOfMemoryByHeap {

	static final int SIZE = 2 * 1024 * 1024;
	public static void main(String[] args) {
		
		int[] i = new int[SIZE];
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
