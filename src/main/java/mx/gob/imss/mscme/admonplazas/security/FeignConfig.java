package mx.gob.imss.mscme.admonplazas.security;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import feign.codec.Decoder;

@Configuration
public class FeignConfig {

	@Bean
	Decoder feignDecoder() {
		return new SpringDecoder(() -> new HttpMessageConverters(new ByteArrayHttpMessageConverter()));
	}
}
