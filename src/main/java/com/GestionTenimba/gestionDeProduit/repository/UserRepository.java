package com.GestionTenimba.gestionDeProduit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.GestionTenimba.gestionDeProduit.modele.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche un utilisateur par son email
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);
}

