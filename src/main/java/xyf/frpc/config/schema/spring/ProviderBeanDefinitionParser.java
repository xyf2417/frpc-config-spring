package xyf.frpc.config.schema.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import xyf.frpc.config.Provider;

public class ProviderBeanDefinitionParser implements BeanDefinitionParser{

	private Log logger = LogFactory.getLog(getClass());
	
	private final static Class<?> BEAN_CLASS = Provider.class;
	
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
        
        String interfaceValue = element.getAttribute("interface");
        
        beanDefinition.getPropertyValues().addPropertyValue("interface", interfaceValue);
        
        
        String ref = element.getAttribute("ref");
        
        if (parserContext.getRegistry().containsBeanDefinition(ref)) {
            BeanDefinition refBean = parserContext.getRegistry().getBeanDefinition(ref);
            if (!refBean.isSingleton()) {
                throw new IllegalStateException("The exported service ref " + ref + " must be singleton! Please set the " + ref + " bean scope to singleton, eg: <bean id=\"" + ref + "\" scope=\"singleton\" ...>");
            }
        }
        Object reference = new RuntimeBeanReference(ref);

        beanDefinition.getPropertyValues().addPropertyValue("target", reference);
        
        
        if(logger.isInfoEnabled())
        {
        	logger.info("Registering the bean with id='" + id + "' into the beanfactory");
        }
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        
		return beanDefinition;
	}


}
