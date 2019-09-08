package cn.lvyjx.test.customtag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.applet.Main;

/**
 * Created by cg on 2019/9/8.
 */
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"tag-application.xml"});
        TestNamespaceBean testBean = context.getBean(TestNamespaceBean.class);
        System.out.println("name"+testBean.getName()+" \n url:"+testBean.getUrl());
    }
}
