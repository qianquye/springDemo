package cn.lvyjx.test.customannotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestAnnotation {
	
	public static void main(String[] args) {
		Class<User> clazz = User.class;
		
		//获取对象的方法
		Method[] methods = clazz.getDeclaredMethods();
		//获取对象的属性
		Field[] declaredFields = clazz.getDeclaredFields();
		
		System.out.println("methods定义个数："+methods.length);
		System.out.println("declaredFields个数:"+declaredFields.length);
		
		
		for(Method method : methods){
			//判断方法是否指定元素的注解
			if(method.isAnnotationPresent(RepMapping.class)){
				//返回 当前方法 上的注解对象
				RepMapping reqMapping = method.getAnnotation(RepMapping.class);
				System.out.println("方法注解的值method:"+reqMapping.method());
				
				//一个注解多个值
				String[] values = reqMapping.val();
				for(String value :values){
					System.out.println("val值："+value);
				}
			}
		}
		
		for(Field declarField : declaredFields){
			if(declarField.isAnnotationPresent(ReqValue.class)){
				ReqValue reqValue = declarField.getAnnotation(ReqValue.class);
				System.out.println("属性注解值1："+reqValue.value1());
				System.out.println("属性注解值2："+reqValue.value2());
			}
		}
		
		//获取类上的所有注解
		Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
		for(Annotation declaredAnnotation : declaredAnnotations){
			System.out.println("类注解中的值："+declaredAnnotation);
		}
	}
}
