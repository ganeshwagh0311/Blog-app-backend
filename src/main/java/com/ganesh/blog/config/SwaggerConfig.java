package com.ganesh.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Configuration
@EnableSwagger2 // ✅ Required annotation for Swagger 2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo(){
        return new ApiInfo("Blogging Application : This is a backend of my project ","This project is developed by Wagh Ganesh","1.0","Term of Service",new Contact("Ganesh ","https://ganeshwagh0311.github.io/portfolio/","waghganesh580@gmail.com"),"License of APIS","API license URL",Collections.emptyList());
    }
}
