package cn.lvyjx.test.postProcessor;

public class Student {

	public String name;
	public String numberId;
	
	public Student(){
		System.out.println("Student construct!");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumberId() {
		return numberId;
	}

	public void setNumberId(String numberId) {
		this.numberId = numberId;
	}
	
	
}
