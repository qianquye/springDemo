package cn.lvyjx.test.customtag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by cg on 2019/9/8.
 */
public class TestNamespaceHandler  extends NamespaceHandlerSupport{

    @Override
    public void init() {
        registerBeanDefinitionParser("visit",new TestBeanDefinitionParser());
    }
}
