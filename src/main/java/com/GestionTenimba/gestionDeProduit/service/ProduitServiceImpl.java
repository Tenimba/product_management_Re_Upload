package com.GestionTenimba.gestionDeProduit.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.GestionTenimba.gestionDeProduit.modele.Produit;
import com.GestionTenimba.gestionDeProduit.repository.ProduitRepository;

@Service

public class ProduitServiceImpl implements ProduitService
{
    private final ProduitRepository produitRepository;
    

    @Autowired
    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit create(Produit produit) 
    {
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> list() 
    {
        return produitRepository.findAll();
    }

    @Override
    public Produit update(Integer id, Produit produit) 
    {
        return produitRepository.findById(id)
                        .map(p ->{
                            p.setPrix(produit.getPrix());
                            p.setNom(produit.getNom());
                            p.setDevise(produit.getDevise());
                            p.setFournisseur(produit.getFournisseur());
                            p.setTaxe(produit.getTaxe());
                            p.setDateExpiration(produit.getDateExpiration());
                            p.setImage(produit.getImage());
                       return  produitRepository.save(p);
                        }).orElseThrow(() -> new RuntimeException("Produit non trouvé..."));
    }

    @Override
    public String delete(Integer id) 
    {
      produitRepository.deleteById(id);
      return "Produit supprimé";
    }
    

    @Override
    public Produit byId(Integer id)
    {
        return produitRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Produit> lireProduitsPagine(int numeroPage, int taillePage, String triPar) 
    {
    
    if (taillePage <= 0) 
    {
        throw new IllegalArgumentException("La taille de la page doit être supérieure à zéro");
    }
     Sort.Direction directionTri = Sort.Direction.ASC;
     String proprieteTri;
     if (triPar != null) 
     {
        if (triPar.startsWith("-")) 
        {
            directionTri = Sort.Direction.DESC;
            proprieteTri = triPar.substring(1);
        } 
        else 
        {
            proprieteTri = triPar;
        }
    }
    else
    {
        proprieteTri = "id";
    }
     PageRequest pageRequest = PageRequest.of(numeroPage, taillePage, directionTri, proprieteTri);
     try {
        return produitRepository.findAll(pageRequest);
    } catch (Exception e) {
        throw new RuntimeException("Erreur lors de la récupération des produits paginés", e);
    }
}


    @Override
    public Page<Produit> search(String recherche, String critere) {
    org.springframework.data.domain.Pageable pageable = PageRequest.of(0,Integer.MAX_VALUE);
    return switch (critere) {
        case "nom" -> produitRepository.findByNomContaining(recherche, pageable);
        case "fournisseur" -> produitRepository.findByFournisseurContaining(recherche, pageable);
        default -> throw new IllegalArgumentException("Critère de recherche non valide: " + critere);
    };
}
}
  


/*
 Le service ProduitServiceImpl implémente l'interface ProduitService et gère la logique métier
 des produits. Il est annoté avec @Service pour l'injection de dépendances Spring.

 Dépendances:
 - ProduitRepository: Interface JPA pour les opérations de base de données

 Fonctionnalités principales:

 1. Opérations CRUD:
    - create(Produit): Crée un nouveau produit
    - list(): Récupère tous les produits
    - update(Integer, Produit): Met à jour un produit existant
    - delete(Integer): Supprime un produit par son ID
    - byId(Integer): Trouve un produit par son ID

 2. Pagination et Tri:
    - lireProduitsPagine(int, int, String): 
      * Gère la pagination des résultats
      * Permet le tri ascendant/descendant
      * Validation des paramètres de page
      * Gestion des erreurs de pagination

 3. Recherche:
    - search(String, String):
      * Recherche par nom ou fournisseur
      * Retourne une page de résultats
      * Gestion des critères invalides

 Gestion des erreurs:
 - Validation des paramètres de pagination
 - Gestion des produits non trouvés
 - Validation des critères de recherche

 Utilisation:
 Le service est utilisé par ProduitController pour:
 - Gérer les requêtes CRUD
 - Implémenter la pagination
 - Permettre la recherche de produits
*/
