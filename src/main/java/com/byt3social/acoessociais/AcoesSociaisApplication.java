package com.byt3social.acoessociais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "API Ações Sociais",
        version = "1.0",
        description = "Api destinada ao gerenciamento das ações sociais"
    )
)
@SpringBootApplication
public class AcoesSociaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcoesSociaisApplication.class, args);
	}

}
