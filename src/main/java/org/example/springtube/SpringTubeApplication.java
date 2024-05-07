package org.example.springtube;

import org.example.springtube.services.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;


@SpringBootApplication
@RestController
public class SpringTubeApplication implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private StreamingService service;

	@GetMapping(value = "templates/{title}", produces = "video/mp4")
	public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
		System.out.println("range in bytes() : " + range);
		return service.getVideo(title);
	}

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