package springDemo.injection;

public class InjectFactory {

	
	public InjectDao getInstance(){
		return new InjectDaoImp();
	}
}
