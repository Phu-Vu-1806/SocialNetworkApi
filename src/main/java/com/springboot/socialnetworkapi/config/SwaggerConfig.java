package com.springboot.socialnetworkapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.name}")
    public String appName;

    @Value("${app.description}")
    private String appDescription;

    @Bean
    public OpenAPI openAPI(){
        final String schemeName = "Bearer ";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(
                        new Components()
                        .addSecuritySchemes(
                                schemeName,
                                new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer ")
                                .bearerFormat("JWT")
                        )
                ).info(apiInfo());
    }

    private Info apiInfo(){
        Info info = new Info();
        info.title(appName);
        info.version("1");
        info.description(appDescription);
        Contact contact = new Contact();
        contact.name("Vu Trong Phu");
        contact.email("vuphu1806@gmail.com");
        info.contact(contact);

        return info;
    }

}
