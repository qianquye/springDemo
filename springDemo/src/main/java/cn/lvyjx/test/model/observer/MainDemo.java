package cn.lvyjx.test.model.observer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by cg on 2019/10/6.
 */
public class MainDemo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"application-oberver.xml"});
        String param1 = "userName";
        String param2 = "password";
        NoticeEvent noticeEvent = new NoticeEvent(new Object(),param1,param2);

        context.publishEvent(noticeEvent);
    }
}
