package xyf.frpc.config.schema.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import xyf.frpc.config.Consumer;

public class ComsumerBeanDefinitionParser implements BeanDefinitionParser {
	
	private final static Class<?> BEAN_CLASS = Consumer.class;

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(BEAN_CLASS);
        beanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        if(id == null || id.length() == 0)
        {
        	throw new IllegalStateException("The consumer element must has the id attribute");
        }
        if (parserContext.getRegistry().containsBeanDefinition(id)) {
            throw new IllegalStateException("Duplicate spring bean id " + id);
        }
        if(name == null || name.length() == 0)
        {
        	name = "Consumer-" + System.currentTimeMillis();
        }
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}

}
