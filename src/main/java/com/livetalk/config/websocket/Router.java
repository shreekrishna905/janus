package com.livetalk.config.websocket;
import org.apache.camel.builder.RouteBuilder;

public class Router extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		  from("activemq:rt_messages").to("bean:queueHandler");
	}

}
