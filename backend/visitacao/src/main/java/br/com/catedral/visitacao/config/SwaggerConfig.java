package br.com.catedral.visitacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@SecurityScheme(name = "Bearer Auth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {
	
	@Bean
	public OpenAPI myOpenAPI() {
		Server server = new Server();
		server.setDescription("Dev Server");

		Contact contact = new Contact();
		contact.setEmail("catedralspadisparador@gmail.com");
		contact.setName("Grupo4 Developers");
		contact.setUrl("https://github.com/WesseleyJr/venda_ingresso");

		License license = new License();
		license.setName("Apache License 2.0");
		license.setUrl("https://www.apache.org/licenses/LICENSE-2.0.html");

		Info info = new Info();
		info.setTitle("Catedral SPA");
		info.setVersion("2.0.0");
		info.setDescription("Aplicativo da Catedral SPA");
		info.setContact(contact);
		info.setLicense(license);
		info.setTermsOfService("https://swagger.io/terms/");

		return new OpenAPI().info(info);
	}

}
