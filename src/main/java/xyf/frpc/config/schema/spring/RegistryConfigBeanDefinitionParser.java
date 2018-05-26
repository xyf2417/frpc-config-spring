package xyf.frpc.config.schema.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import xyf.frpc.config.RegistryConfig;

public class RegistryConfigBeanDefinitionParser implements BeanDefinitionParser{
	private Log logger = LogFactory.getLog(getClass());
	
	private final static Class<?> BEAN_CLASS = RegistryConfig.class;
	
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
        
        
        String type = element.getAttribute("type");
        beanDefinition.getPropertyValues().addPropertyValue("type", type);
        
        String address = element.getAttribute("address");
        //TODO: valid the address
        beanDefinition.getPropertyValues().addPropertyValue("address", address);
        
        if(logger.isInfoEnabled())
        {
        	logger.info("Registering the bean with id='" + id + "' into the beanfactory");
        }
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}

}
