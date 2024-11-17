/**
 * Service pour la gestion des produits.
 */
package com.GestionTenimba.gestionDeProduit.service;
 
import java.util.List;

import org.springframework.data.domain.Page;

import com.GestionTenimba.gestionDeProduit.modele.Produit;


 public interface ProduitService {
     /**
     * Crée un nouveau produit.
     *
     * @param produit le produit à créer
     * @return le produit créé
     */
    Produit create(Produit produit);


     /**
     * Récupère la liste de tous les produits.
     *
     * @return la liste des produits
     */
    List<Produit> list();


     /**
     * Modifie un produit existant.
     *
     * @param id l'identifiant du produit à modifier
     * @param produit les nouvelles informations du produit
     * @return le produit modifié
     */
    Produit update(Integer id, Produit produit);

     /**
     * Supprime un produit existant.
     *
     * @param id l'identifiant du produit à supprimer
     * @return un message indiquant si la suppression a été effectuée avec succès
     */
    String delete(Integer id);

    
     /**
     * Trouve un produit par son identifiant.
     *
     * @param id l'identifiant du produit à trouver
     * @return le produit correspondant à l'identifiant
     */
    Produit byId(Integer id);


    Page<Produit> lireProduitsPagine(int numeroPage, int taillePage, String triPar) ;


    public Page<Produit> search(String recherche, String critere);
}