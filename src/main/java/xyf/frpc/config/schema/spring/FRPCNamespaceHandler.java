package xyf.frpc.config.schema.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class FRPCNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
        registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser());
        registerBeanDefinitionParser("protocol", new ProtocolConfigBeanDefinitionParser());
        registerBeanDefinitionParser("registry", new RegistryConfigBeanDefinitionParser());
        registerBeanDefinitionParser("application", new ApplicationBeanDefinitionParser());
        registerBeanDefinitionParser("provider", new ProviderBeanDefinitionParser());
    }
}
