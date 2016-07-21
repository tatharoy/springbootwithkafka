package com.davita.pie.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.davita.pie.*" })
public class SpringBootDemoApplication extends SpringBootServletInitializer  {

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootDemoApplication.class);
	}
	
	public static void main(String[] args) {

		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
