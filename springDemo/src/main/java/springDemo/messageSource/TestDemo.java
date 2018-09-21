package springDemo.messageSource;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDemo {
 
	public static void main(String[] arags){
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		String info1 = context.getMessage("info",new String[]{"1"}, Locale.getDefault());
		String info2 = context.getMessage("info",new String[]{"1"}, Locale.ENGLISH);
		String info3 = context.getMessage("info",new String[]{"1"},Locale.CHINESE);
		System.out.println("info1:"+info1);
		System.out.println("info2:"+info2);
		System.out.println("info3:"+info3);
	}
}
