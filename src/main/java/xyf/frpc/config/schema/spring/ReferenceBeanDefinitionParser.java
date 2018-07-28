package xyf.frpc.config.schema.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import xyf.frpc.config.Reference;

public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {

	private final static Log logger = LogFactory
			.getLog(ReferenceBeanDefinitionParser.class);

	private final static Class<?> BEAN_CLASS = Reference.class;

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

		String name = element.getAttribute("name");
		if (name == null || name.length() == 0) {
			name = id;
		}
		beanDefinition.getPropertyValues().addPropertyValue("name", name);
		
		String host = element.getAttribute("host");
		if(host != null && host.length() > 0){
			beanDefinition.getPropertyValues().addPropertyValue("host", host);
		}
		
		String port = element.getAttribute("port");
		if(port != null && port.length() > 0) {
	        int iport = 0;
	        try {
				iport = Integer.parseInt(port);
				beanDefinition.getPropertyValues().addPropertyValue("port", iport);
			} catch (Throwable e) {
				throw new IllegalStateException("The invalid attribute value '" + port + "' of port");
			}
		}
        
		
		String interfaceValue = element.getAttribute("interface");

		beanDefinition.getPropertyValues().addPropertyValue("interface",
				interfaceValue);
		
		String mock = element.getAttribute("mock");
		
		if(mock != null && mock.length() > 0) {
			Object mockRef = new RuntimeBeanReference(mock);

	        beanDefinition.getPropertyValues().addPropertyValue("mock", mockRef);
		}
		
		String stub = element.getAttribute("stub");
		
		if(mock != null && mock.length() > 0) {
			Class stubClass;
			try {
				stubClass = Class.forName(stub);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("Can't find the stubClass of: " + stub);
			}

	        beanDefinition.getPropertyValues().addPropertyValue("stubClass", stubClass);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("frpc: Registering the Reference with id='" + id
					+ "' into the beanfactory");
		}
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return beanDefinition;
	}

}
