package com.taild.employeeservice.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Service API",
                version = "1.0",
                description = "API for employee services",
                contact = @Contact(
                        name = "Duc Tai",
                        email = "lductai98@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
