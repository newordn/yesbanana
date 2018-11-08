package com.derteuffel;




import com.derteuffel.controller.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class CollectApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CollectApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CollectApiApplication.class);
	}
}
