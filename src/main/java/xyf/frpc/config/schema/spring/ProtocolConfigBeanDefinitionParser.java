package xyf.frpc.config.schema.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import xyf.frpc.config.ProtocolConfig;

public class ProtocolConfigBeanDefinitionParser implements BeanDefinitionParser{

	private final static Log logger = LogFactory.getLog(ProtocolConfigBeanDefinitionParser.class);
	
	private final static Class<?> BEAN_CLASS = ProtocolConfig.class;
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(BEAN_CLASS);
        beanDefinition.setLazyInit(false);
        
        
        String id = element.getAttribute("id");
		if (id == null || id.length() <= 0) {
			id = BEAN_CLASS.getName() + "-" + System.currentTimeMillis();
			if (parserContext.getRegistry().containsBeanDefinition(id)) {
				throw new IllegalStateException("Duplicate spring bean id "
						+ id);
			}
		}
		beanDefinition.getPropertyValues().addPropertyValue("id", id);
 
        String port = element.getAttribute("port");
        int iport = 0;
        try {
			iport = Integer.parseInt(port);
		} catch (Throwable e) {
			throw new IllegalStateException("The invalid attribute value '" + port + "' of port");
		}
        beanDefinition.getPropertyValues().addPropertyValue("port", iport);
        
        String name = element.getAttribute("name");
//        if(name == null || name == "")
//        {
//        	name = ConfigConstants.DEFAULT_PROTOCOL_NAME;
//        }
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        
        
        if(logger.isInfoEnabled())
        {
        	logger.info("frpc: Registering the bean with id='" + id + "' into the beanfactory");
        }
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}


}
