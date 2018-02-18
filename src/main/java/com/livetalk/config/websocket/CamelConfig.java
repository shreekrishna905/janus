package com.livetalk.config.websocket;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends CamelConfiguration {

	 public static void main(String[] args) throws Exception {
	        Main main = new Main();
	        main.setConfigClass(CamelConfig.class);
	        main.run();
	} 
	 @Override
     protected void setupCamelContext(CamelContext camelContext) throws Exception {
        // setup the ActiveMQ component
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:8161");
        // and register it into the CamelContext
        JmsComponent answer = new JmsComponent();
        answer.setConnectionFactory(connectionFactory);
        camelContext.addComponent("jms", answer);
     }
	 
}
