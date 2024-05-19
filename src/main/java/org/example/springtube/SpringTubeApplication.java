
package org.example.springtube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@SpringBootApplication
public class SpringTubeApplication implements ApplicationListener<ContextRefreshedEvent> {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}




	public static void main(String[] args) {
		SpringApplication.run(SpringTubeApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		try {
			Runtime.getRuntime().exec("xdg-open http://localhost:3030/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
