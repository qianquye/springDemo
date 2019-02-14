package cn.lvyjx.test.postProcessor;

public class Teacher {

	public String name;
	public String phone;
	
	public Teacher(){
		System.out.println("Teacher construct!");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("teacher中name值的注入");
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "name="+name+";phone="+phone;
	}
	
	/**
	 * 初始化方法 
	 */
	public void init(){
		System.out.println("Teacher-init method");
	}

	
	
}
