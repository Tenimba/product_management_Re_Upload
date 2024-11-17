package com.GestionTenimba.gestionDeProduit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class GestionDeProduitApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(GestionDeProduitApplication.class, args);
	}

	@Configuration
	public class StaticResourceConfiguration implements WebMvcConfigurer 
	{
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/Images/**")
					.addResourceLocations("classpath:/static/Images/");
		}

		
	}

}