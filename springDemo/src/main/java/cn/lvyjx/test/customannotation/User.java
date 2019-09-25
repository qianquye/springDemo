package cn.lvyjx.test.customannotation;

@RepMapping(method=ReqMethod.POST,val="类")
public class User {

	@ReqValue(value1="张三")
	private String userName;
	
	@ReqValue(value2="密码")
	private String pswd;
	
	private int age;
	
	@RepMapping(method = ReqMethod.GET)
	public void get(){
		
	}
	
	@RepMapping(method = ReqMethod.POST)
	public void post(){
			
	}
	
	@RepMapping(val={"a","b"})
	public void other(){
		
	}
	
	public void noAnnotation(){}
}
