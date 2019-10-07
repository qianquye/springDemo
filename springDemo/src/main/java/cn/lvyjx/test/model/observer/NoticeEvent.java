package cn.lvyjx.test.model.observer;

import org.springframework.context.ApplicationEvent;

/**
 * Created by cg on 2019/10/6.
 */
public class NoticeEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */

    private String param1;
    private String param2;

    public NoticeEvent(Object source) {
        super(source);
    }

    public NoticeEvent(Object source,String param1,String param2){
        super(source);
        this.param1 = param1;
        this.param2 = param2;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
