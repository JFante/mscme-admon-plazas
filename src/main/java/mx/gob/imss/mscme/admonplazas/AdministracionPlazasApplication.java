package mx.gob.imss.mscme.admonplazas;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@EnableAsync
@EnableFeignClients
@SpringBootApplication
public class AdministracionPlazasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdministracionPlazasApplication.class, args);
	}
	
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
    }

    
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
