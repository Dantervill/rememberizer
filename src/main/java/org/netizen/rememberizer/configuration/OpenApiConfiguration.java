package org.netizen.rememberizer.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    private final String devUrl;

    public OpenApiConfiguration(@Value("${rememberizer.openapi.dev-url}") String devUrl) {
        this.devUrl = devUrl;
    }

    @Bean
    public OpenAPI openAPI() {
        final Server devServer = new Server().url(devUrl).description("Server URL in Development environment");
        final Contact contact = new Contact().email("nagibinvlas@gmail.com").name("Vlas Nagibin");
        final License mitLicence = new License().name("MIT Licence").url("https://choosealicense.com/licenses/mit/");
        final Info info = new Info().title("Note Management REST API").version("1.0").contact(contact)
                .description("This REST API exposes endpoints to manage notes.").license(mitLicence);
        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
