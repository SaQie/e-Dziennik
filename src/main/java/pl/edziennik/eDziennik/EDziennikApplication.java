package pl.edziennik.eDziennik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EDziennikApplication {

	public static void main(String[] args) {
		SpringApplication.run(EDziennikApplication.class, args);
	}
}
