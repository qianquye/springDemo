package cn.lvyjx.test.customtag;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by cg on 2019/9/8.
 */
public class TestBeanDefinitionParser extends AbstractBeanDefinitionParser {


    @Override
    protected final AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(TestNamespaceBean.class);
        setTestName(element,builder);
        setTestUrl(element,builder);
        return null;
    }

    private void setTestName(Element element,BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        if(StringUtils.hasText(name)){
            builder.addPropertyValue("name",name);
        }
    }

    private void setTestUrl(Element element,BeanDefinitionBuilder builder){
        String url = element.getAttribute("url");
        if(StringUtils.hasText(url)){
            builder.addPropertyValue("url",url);
        }
    }

}
