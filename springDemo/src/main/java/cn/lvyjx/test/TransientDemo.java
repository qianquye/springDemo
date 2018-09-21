package cn.lvyjx.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 定义了transient的变量，当对象被序列化时，变量也不会在网络上传输
 * transient只修饰变量，不修饰方法或类，本地变量也是不被transient修饰的。自定义的类变量被修饰
 * @author Administrator
 *
 */
public class TransientDemo {

	public static void main(String[] args) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername("张三");
		userInfo.setPwd("123132");
		System.out.println("userinfo:"+userInfo.toString());
		
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\userinfo.txt"));
			out.writeObject(userInfo);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\userinfo.txt"));
			UserInfo userInfor = (UserInfo)in.readObject();
			System.out.println(userInfor.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private transient String pwd;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", pwd=" + pwd + "]";
	}
	
	
	
}
