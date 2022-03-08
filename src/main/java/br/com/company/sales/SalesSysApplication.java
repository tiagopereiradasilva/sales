package br.com.company.sales;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Vendas - API.",
				description = "Api para gerenciamento de sistema de pedidos envolvendo clientes e produtos.",
				version = "1.0",
				contact = @Contact(
						name = "Tiago Pereira da Silva",
						email = "tiagopereiracx@gmail.com",
						url = "https://github.com/tiagopereiradasilva"
				)
		)
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class SalesSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesSysApplication.class, args);
	}

}
