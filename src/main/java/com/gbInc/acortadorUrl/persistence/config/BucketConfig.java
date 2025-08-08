package com.gbInc.acortadorUrl.persistence.config;

import io.github.bucket4j.Bandwidth;
import org.springframework.context.annotation.Configuration;
import io.github.bucket4j.Bucket;
import java.time.Duration;
import org.springframework.context.annotation.Bean;

@Configuration
public class BucketConfig {

	@Bean
	public Bucket bucket() {

		Bandwidth limit = Bandwidth.builder().capacity(10).refillIntervally(10, Duration.ofMinutes(1)).build();

		return Bucket.builder().addLimit(limit).build();

	}

}
