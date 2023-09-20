package pl.edziennik.infrastructure.spring.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI eDziennikApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-diary documentation")
                        .description("REST-API documentation for E-diary")
                        .version("v0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("E-diary github")
                        .url("https://github.com/SaQie/e-Dziennik")
                );
    }

}
