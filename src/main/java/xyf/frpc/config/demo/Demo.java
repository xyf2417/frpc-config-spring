package xyf.frpc.config.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyf.frpc.config.Consumer;


public class Demo {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring-demo.xml"});
        context.start();
        Consumer consumer = (Consumer) context.getBean("consumer"); // get remote service proxy
	}

}
