package com.gbInc.acortadorUrl.persistence.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CoorsConfig implements WebMvcConfigurer {

	@Value("${user.allowed.coors.origin}")
	private String allowedOrigin;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		String[] manyOrigins = this.allowedOrigin.split(" ");
		
		registry.addMapping("/**")
				.allowedOrigins(manyOrigins)
				.allowedMethods("GET","POST","PUT","DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true);
	
	}
	
	
}
