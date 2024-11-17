package com.GestionTenimba.gestionDeProduit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.GestionTenimba.gestionDeProduit.modele.User;
import com.GestionTenimba.gestionDeProduit.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class UserController 
{
    @Autowired
    private final UserRepository userRepository;
    
    // Affiche le formulaire d'inscription
    @GetMapping("/register")
    public String showRegistrationForm(Model model) 
    {
        model.addAttribute("user", new User());
        return "createUser";
    }

    // Traitement de l'inscription
    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute User user) 
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return "register_success";
    }

    // Affiche la liste des utilisateurs
    @GetMapping("/users")
    public String listUsers(Model model) 
    {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    // Supprime un utilisateur
    @GetMapping("/deleteUser/{id}")
    public String deleteProduct(@PathVariable(value="id") long id) 
    {
        this.userRepository.deleteById(id);
        return "redirect:/users";
    }
}


/*
 Le contrôleur UserController gère les opérations liées aux utilisateurs.

 Il est annoté avec:
 - @Controller pour indiquer qu'il est un composant contrôleur Spring MVC
 - @AllArgsConstructor de Lombok pour l'injection de dépendances

 Les dépendances:
 - UserRepository pour les opérations CRUD sur les utilisateurs
 - BCryptPasswordEncoder pour le hashage des mots de passe

 Les principales fonctionnalités:

 1. Gestion des inscriptions:
    - GET "/register" : Affiche le formulaire d'inscription (createUser.html)
    - POST "/process_register" : Traite l'inscription avec hashage du mot de passe
    Le mot de passe est encodé avec BCrypt avant enregistrement en base de données.

 2. Gestion des utilisateurs:
    - GET "/users" : Liste tous les utilisateurs (users.html)
    - GET "/deleteUser/{id}" : Supprime un utilisateur et redirige vers la liste

 Workflow:
 1. L'utilisateur accède au formulaire d'inscription via /register
 2. Les données sont soumises à /process_register
 3. Le mot de passe est hashé avec BCrypt
 4. L'utilisateur est enregistré en base de données
 5. Redirection vers la page de succès

 Les administrateurs peuvent:
 - Voir la liste des utilisateurs via /users
 - Supprimer des utilisateurs via /deleteUser/{id}

 Le contrôleur utilise Thymeleaf pour le rendu des vues:
 - createUser.html : Formulaire d'inscription
 - register_success.html : Page de confirmation
 - users.html : Liste des utilisateurs
*/