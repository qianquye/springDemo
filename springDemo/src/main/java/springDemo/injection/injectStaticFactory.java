package springDemo.injection;

public class injectStaticFactory {

	
	public static final InjectDao getInjectDao(){
		return new InjectDaoImp();
	}
}
