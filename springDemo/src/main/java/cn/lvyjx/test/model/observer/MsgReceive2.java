package cn.lvyjx.test.model.observer;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by cg on 2019/10/6.
 */
public class MsgReceive2 implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        System.out.println("----------接收信息者2----------");
        if(event instanceof NoticeEvent){
            NoticeEvent  noticeEvent = (NoticeEvent)event;
            System.out.println("param1:"+noticeEvent.getParam1());
            System.out.println("param2:"+noticeEvent.getParam2());
        }
    }
}
