package nakisa.saman.pizzaorder;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class PizzaOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaOrderApplication.class, args);
	}

	

}
