package pl.edziennik.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"pl.edziennik.infrastructure"})
@EntityScan(basePackages = {"pl.edziennik.domain"})
@ComponentScan(basePackages = {"pl.edziennik.infrastructure", "pl.edziennik.domain", "pl.edziennik.application",
        "pl.edziennik.web"})
@PropertySource("/application.yaml")
public class EDziennikApplication {

    public static void main(String[] args) {
        SpringApplication.run(EDziennikApplication.class, args);
    }


}
