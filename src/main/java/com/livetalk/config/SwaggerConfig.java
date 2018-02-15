package com.livetalk.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource(value= {"classpath:application.properties"})
public class SwaggerConfig {

	@Value("${app.auth.link}")
	private String authLink;
	
	@Bean
	public Docket api() {
		List<ResponseMessage> list = new java.util.ArrayList<>();
        list.add(new ResponseMessageBuilder().code(500).message("500 message")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
                .responseModel(new ModelRef("Result")).build());
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.trustaml.fingerprint"))
                .paths(PathSelectors.any()).build().securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
                .useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
                .globalResponseMessage(RequestMethod.POST, list);
		}
	
	private OAuth securitySchema() {
	 	List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));
        List<GrantType> grantTypes = new ArrayList<>();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink+"/oauth/token");
        grantTypes.add(creGrant);
        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
    }
	 
 	private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**")).build();
    }

   private List<SecurityReference> defaultAuth() {
	    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
	    authorizationScopes[0] = new AuthorizationScope("read", "read all");
	    authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
	    authorizationScopes[2] = new AuthorizationScope("write", "write all");
	    return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
   }
	    
  @Bean
  public SecurityConfiguration securityInfo() {
    return new SecurityConfiguration("", "", "", "", "", ApiKeyVehicle.HEADER, "", " ");
  }
 
  private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Trust Check API")
                .description("Finger print enrollment and verification")
                .termsOfServiceUrl("https://www.datum.com.np")
                .contact(new Contact("Shree Krishna Poudel",
                                     "http://www.datum.com", "shree@datum.com"))
                .license("TrustAML")
                .licenseUrl("https://www.datum.com.np")
                .version("1.0.0")
                .build();
    }

	 //This bean needed to resolve ${property.name} syntax
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
