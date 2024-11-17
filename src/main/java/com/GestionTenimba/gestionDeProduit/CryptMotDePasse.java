package com.GestionTenimba.gestionDeProduit;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CryptMotDePasse implements WebMvcConfigurer 
{

    // La méthode addViewControllers permet de configurer les associations entre les URLs et les vues.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");

        registry.addViewController("/").setViewName("home");

        registry.addViewController("/login").setViewName("Se connecter");

        registry.addViewController("/").setViewName("Bienvenue");

        registry.addViewController("/register").setViewName("S'inscrire");
    }
}
