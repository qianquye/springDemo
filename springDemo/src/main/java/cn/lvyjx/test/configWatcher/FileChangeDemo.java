package cn.lvyjx.test.configWatcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileChangeDemo {

	public static void main(String[] args) {
		
		try {
			Files
			.list(Paths.get("E:\\dev"))
			.filter(Files::isDirectory)
			.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
