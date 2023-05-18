package pl.edziennik;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edziennik.infrastructure.spring.authentication.security.SecurityBeans;
import pl.edziennik.infrastructure.spring.authentication.security.SecurityConfiguration;

@Configuration
@EnableJpaRepositories(basePackages = {"pl.edziennik.*"})
@EntityScan(basePackages = {"pl.edziennik.domain"})
@ComponentScan(basePackages = {"pl.edziennik.*"})
@Import({AuthenticationConfiguration.class, SecurityBeans.class, SecurityConfiguration.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureDataJpa
public class TestConfig {

}
