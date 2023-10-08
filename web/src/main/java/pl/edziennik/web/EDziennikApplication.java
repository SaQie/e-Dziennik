package pl.edziennik.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = RedisAutoConfiguration.class)
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"pl.edziennik.infrastructure"})
@EntityScan(basePackages = {"pl.edziennik.domain"})
@ComponentScan(basePackages = {"pl.edziennik.infrastructure", "pl.edziennik.domain", "pl.edziennik.application",
        "pl.edziennik.web", "pl.edziennik.common"})
@PropertySource("/application.yml")
public class EDziennikApplication {

    public static void main(String[] args) {
        SpringApplication.run(EDziennikApplication.class, args);
    }


}
