package ar.edu.unq.desapp.grupof.backendcriptop2papi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class BackendCriptop2pApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendCriptop2pApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
