package xyf.frpc.config.schema.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import xyf.frpc.config.Application;

public class ApplicationBeanDefinitionParser implements BeanDefinitionParser{

	private Log logger = LogFactory.getLog(getClass());
	
	private final static Class<?> BEAN_CLASS = Application.class;
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(BEAN_CLASS);
        beanDefinition.setLazyInit(false);
        
        String id = element.getAttribute("id");
        if(id == null || id.length() == 0)
        {
        	throw new IllegalStateException("The protocol element must has the id attribute");
        }
        if (parserContext.getRegistry().containsBeanDefinition(id)) {
            throw new IllegalStateException("Duplicate spring bean id " + id);
        }
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        
        
        String name = element.getAttribute("name");

        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        
        
        if(logger.isInfoEnabled())
        {
        	logger.info("Registering the bean with id='" + id + "' into the beanfactory");
        }
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}


}