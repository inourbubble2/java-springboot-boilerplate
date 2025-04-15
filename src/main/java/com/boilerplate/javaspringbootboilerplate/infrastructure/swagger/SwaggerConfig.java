package com.boilerplate.javaspringbootboilerplate.infrastructure.swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@OpenAPIDefinition(
    info = @Info(
        title = "boilerplate-api"
    )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        val securityScheme = new SecurityScheme()
            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
            .name("Authorization");

        val securityRequirement = new SecurityRequirement()
            .addList("bearerAuth");

        return new OpenAPI()
            .components(
                new Components()
                    .addSecuritySchemes("bearerAuth", securityScheme)
            )
            .security(Collections.singletonList(securityRequirement));
    }
}
