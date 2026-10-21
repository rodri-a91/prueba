package ceu.api.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ceu")
@EntityScan("ceu")
public class UserApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApiServerApplication.class, args);
	}

}
