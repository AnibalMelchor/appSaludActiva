package com.example.appsaludactiva.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("App Salud Activa - API Rest")
                        .description("API REST para la gestión de alimentación, perfiles de salud y hábitos nutricionales.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Soporte Técnico Anibal Melchor Cordero")
                                .email("melchorcordeeroanibal@gmail.com")))

                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))

                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }


}
