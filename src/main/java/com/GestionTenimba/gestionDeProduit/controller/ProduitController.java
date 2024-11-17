package com.GestionTenimba.gestionDeProduit.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.GestionTenimba.gestionDeProduit.modele.Produit;
import com.GestionTenimba.gestionDeProduit.service.ProduitService;

import lombok.AllArgsConstructor;




@Controller
@AllArgsConstructor

public class ProduitController 
{
    private final ProduitService produitService;

    // Méthode pour afficher la liste des produits
    @RequestMapping("/")
    public String index() 
    {
        return "index";
    }

    // Méthode pour afficher la liste des produits
    @RequestMapping("/listeAvecCon")
    public String liste(Model model) 
    {
        return listePaginee(model, 0, 4, "id", "NULL");
    }
    @RequestMapping("/listeSansCon")
    public String liste2(Model model) 
    {
        return listePaginee(model, 0, 4, "id", "NULL");
    }

    // Méthode pour aller à la page de création d'un nouveau produit
    @RequestMapping("/creation/")
    public String productCreate() 
    {
        return "create";
    }

     // Méthode pour aller à la page de mise à jour d'un produit
     @RequestMapping("/create/{id}")
     public String update(Model model, @PathVariable Integer id) {
         Produit produit = produitService.byId(id);
         if (produit == null) {
             model.addAttribute("errorMessage", "Produit non trouvé");
             return "error";
         }
         model.addAttribute("produit", produit);
         return "create";
     }
     
    // Méthode pour lire la liste des produits avec pagination
    @RequestMapping(value = {"/liste/{page}/{pageSize}", "/liste/{page}/{pageSize}/{sortBy}"}, method = RequestMethod.GET)
    public String listePaginee(Model model, @PathVariable int page, @PathVariable int pageSize, @PathVariable String sortBy,@RequestParam( required = false, defaultValue = "NULL") String param) 
    {
         if (page < 0 || pageSize < 1) 
        {
            model.addAttribute("errorMessage", "Page ou taille de page non valide");
            return "error";
        }
        if (page < 0) 
        {
            model.addAttribute("errorMessage", "Cette page n'existe pas!!!");
            return "error";
        }
        Page<Produit> produitsPage = produitService.lireProduitsPagine(page, pageSize, sortBy);
        if (page > produitsPage.getTotalPages()) 
        {
            model.addAttribute("errorMessage", "Cette page n'existe pas!!!");
            return "error";
        }
        String imagePath=System.getProperty("user.dir") + "/Gestion_Produit/src/main/resources/images";
        if (!"NULL".equals(param))
        {
             model.addAttribute("message", param);
        }
        model.addAttribute("imagePath", imagePath);
        model.addAttribute("produits", produitsPage);
        model.addAttribute("sortBy", sortBy);
        return "list";
    }
   
   
    // Méthode pour créer un produit avec une image
    @PostMapping({"/create", "/create/{id}"})
    public String createOuUpdate(@ModelAttribute Produit produit, 
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            @PathVariable(required = false) Integer id,
                            Model model) throws IOException {
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String nomImage = saveImage(imageFile);
            produit.setImage(nomImage);
            
            if (id != null) {
                Produit ancienProduit = produitService.byId(id);
                if (ancienProduit != null && ancienProduit.getImage() != null) {
                    deleteImage(ancienProduit.getImage());
                }
            }
        } else if (id != null) {
            Produit ancienProduit = produitService.byId(id);
            if (ancienProduit != null) {
                produit.setImage(ancienProduit.getImage());
            }
        }
        
        if (id != null) {
            produit.setId(id);
            produitService.update(id, produit);
            model.addAttribute("message", "Le produit a bien été modifié!");
        } else {
            produitService.create(produit);
            model.addAttribute("message", "Le produit a bien été créé!, MERCI DE RECHARGER LA PAGE POUR VOIR LES IMAGES");
        }
        
        Page<Produit> produitsPage = produitService.lireProduitsPagine(0, 4, "id");
        model.addAttribute("produits", produitsPage);
        model.addAttribute("sortBy", "id");
        
        String imagePath = "/Images";
        model.addAttribute("imagePath", imagePath);
        read();
        return "list";
    }
        
    // Méthode pour sauvegarder une image
    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/Images/";
        
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return fileName;
    }
    // Méthode pour récupérer toute la liste des produits
    @GetMapping(value = "/read")
    public List<Produit> read() 
    {
        return produitService.list();
    }

    // Méthode pour supprimer une image du systeme de fichier  
    public void deleteImage(String nomImage) 
    {
        String cheminFichier = System.getProperty("user.dir") + "/Gestion_Produit/src/main/resources/static/Images/" + nomImage;
    
        File fichierImage = new File(cheminFichier);
    
        if (fichierImage.exists()) 
        {
            fichierImage.delete();
           
        } 
    }

    // Méthode pour supprimer un produit de la bdd
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) 
    {
        if (produitService.byId(id) != null) 
        {
            produitService.delete(id);
            return true;
        }
        return false;
    }
   
    // Méthode pour supprimer un produit depuis l'interface et rediriger vers l'accueil
    @RequestMapping("/delete/{id}")
    public String suppression(Model model, @PathVariable Integer id) {
        Produit produit = produitService.byId(id);
        if (produit != null) {
            // Supprimer l'image si elle existe
            if (produit.getImage() != null) {
                deleteImage(produit.getImage());
            }
            
            // Supprimer le produit
            produitService.delete(id);
            
            // Retourner à la liste avec message de confirmation
            return listePaginee(model, 0, 4, "id", "Le produit a été supprimé avec succès");
        } else {
            model.addAttribute("errorMessage", "Produit non trouvé");
            return "error";
        }
    }

    // Méthode pour rechercher des produits
    @PostMapping("/recherche")
    public String recherche(@RequestParam("recherche") String recherche, @RequestParam(value = "critere", required = false) String critere, Model model) 
    {
        if (critere == null || critere.isEmpty()) {
            return listePaginee(model, 0, 4, "id", "Veuillez sélectionner un critère de recherche");
        }
        Page<Produit> produits = produitService.search(recherche, critere);

            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", produits.getTotalPages());
            model.addAttribute("totalItems", produits.getTotalElements());

            if (produits.getTotalElements() == 0) 
            {
                model.addAttribute("message", "Aucun résultat pour votre recherche!!");
            }
        
        model.addAttribute("produits", produits);
        return "list";
    }
}

/*
 Le contrôleur ProduitController gère les requêtes liées aux produits.

 Il est annoté avec @Controller pour indiquer qu'il est un composant du contrôleur dans Spring MVC. 
 Il utilise @AllArgsConstructor de Lombok pour l'injection de dépendances du ProduitService.

 Le constructeur de la classe prend en paramètre un objet ProduitService, qui est utilisé pour effectuer 
 les opérations CRUD sur les produits.

 Les principales fonctionnalités incluent:

 1. Navigation:
    - "/" : Page d'accueil (index.html)
    - "/listeAvecCon" : Liste des produits pour utilisateurs connectés
    - "/listeSansCon" : Liste des produits publique
    - "/creation/" : Page de création de produit

 2. Gestion des produits:
    - Création/Modification (@PostMapping("/create", "/create/{id}"))
    - Lecture avec pagination ("/liste/{page}/{pageSize}")
    - Suppression (@DeleteMapping("/{id}") et "/delete/{id}")
    - Recherche (@PostMapping("/recherche"))

 3. Gestion des images:
    - Upload d'images avec UUID unique
    - Stockage dans /src/main/resources/static/Images/
    - Suppression automatique des anciennes images

 4. Fonctionnalités avancées:
    - Pagination des résultats
    - Tri des produits
    - Messages de confirmation
    - Gestion des erreurs

Le contrôleur utilise Thymeleaf pour le rendu des vues (index.html, list.html, create.html, error.html)
et gère les interactions avec le service ProduitService pour les opérations de base de données.
*/
