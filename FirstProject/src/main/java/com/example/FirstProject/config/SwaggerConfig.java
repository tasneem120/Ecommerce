package com.example.FirstProject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerSchem=new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Bearer Token");
        SecurityRequirement bearerRequirement=new SecurityRequirement()
                .addList("Bearer Authentication");
return new OpenAPI()
        .info(new Info()
             .title("Spring Boot eCommerce API")
             .description("This is a Spring Boot eCommerce API")
             .version("1.0")
                .license(new License().name("Apache 2.0").url("http://url.com"))
                .contact(new Contact().name("Tasneem Khaled")
                        .email("ktasneem074@gmail.com")
                        .url("https://github.com/tasneem120")
                )
        )
        .components(new Components().
        addSecuritySchemes("Bearer Authentication",bearerSchem)).addSecurityItem(bearerRequirement);

    }
}
