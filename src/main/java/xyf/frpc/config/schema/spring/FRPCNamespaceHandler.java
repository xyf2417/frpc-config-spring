package xyf.frpc.config.schema.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class FRPCNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
        registerBeanDefinitionParser("consumer", new ComsumerBeanDefinitionParser());
        registerBeanDefinitionParser("protocol", new ProtocolConfigBeanDefinitionParser());
        registerBeanDefinitionParser("registry", new RegistryConfigBeanDefinitionParser());
    }
}
