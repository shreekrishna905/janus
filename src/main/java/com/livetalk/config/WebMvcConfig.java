package com.livetalk.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.livetalk")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
	 
	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	  @Bean
	  @Primary
	  public SimpUserRegistry userRegistry() {
	    return  new DefaultSimpUserRegistry();
	  }

	  @Bean
	  @Primary
	  public UserDestinationResolver userDestinationResolver() {
	    return new DefaultUserDestinationResolver(userRegistry());
	  }
	
}