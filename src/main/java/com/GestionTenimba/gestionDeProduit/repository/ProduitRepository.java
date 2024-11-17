package com.GestionTenimba.gestionDeProduit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionTenimba.gestionDeProduit.modele.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    Page<Produit> findByNomContaining(String recherche, org.springframework.data.domain.Pageable pageable);
    
    Page<Produit> findByPrixContaining(Integer recherche, org.springframework.data.domain.Pageable pageable);
    
    Page<Produit> findByFournisseurContaining(String recherche, org.springframework.data.domain.Pageable pageable);
    
    Page<Produit> findByDateExpirationContaining(String recherche, org.springframework.data.domain.Pageable pageable);
}
