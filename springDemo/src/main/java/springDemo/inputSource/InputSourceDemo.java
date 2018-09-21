package springDemo.inputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.xml.sax.InputSource;

public class InputSourceDemo {

	public static void main(String[] args) {
		String path = "D:\\workspaceYP\\application.xml";
		readFile(path);
	}
	
	public static void readFile(String path){
		if(path == null) return;
		InputStream input = null;
		try{
			File file = new File(path);
			input = new FileInputStream(file);
			InputSource inputSource = new InputSource(input);
			System.out.println(inputSource.getEncoding());
		}catch(Exception e){
			
		}
	}
}
