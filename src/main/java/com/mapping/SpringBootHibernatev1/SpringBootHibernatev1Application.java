package com.mapping.SpringBootHibernatev1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringBootHibernatev1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernatev1Application.class, args);
	}

	// Otra forma de configurar CORS
	// @SpringBootApplication
	// public class DemoApplication {
	// public static void main(String[] args) {
	// SpringApplication.run(DemoApplication.class, args);
	// }
	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// return new WebMvcConfigurerAdapter() {
	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/products").allowedOrigins("http://localhost:8080");
	// }
	// };
	// }
	// }

}
