# Gestion de Produits
CE PROJECT EST UN RE-UPLOAD D'UN PROJECT REALISER AU COURS DE MA SCOLARITE A EPITECH

Application web de gestion de produits développée avec Spring Boot et Thymeleaf dans le cadre d'un projet Epitech.

## Fonctionnalités

- Authentification utilisateur sécurisée
- Opérations CRUD sur les produits
- Gestion des uploads d'images
- Système de recherche avancé par nom et fournisseur
- Pagination des résultats
- Tri dynamique des données

## Technologies Utilisées

- Java 17
- Spring Boot 3.3.5
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Bootstrap 5
- Maven
- Junit

## Installation

1. Clonez le dépôt
```bash
git clone [url-du-projet]
cd gestionDeProduit
```

2. Créez la base de données MySQL
```sql
CREATE DATABASE gestion_de_produit;
```

3. Configurez la base de données dans `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_de_produit
spring.datasource.username=votre_username
spring.datasource.password=votre_password
```

4. Lancez l'application
```bash
./mvnw spring-boot:run
```

## Structure du Projet

```
src/
├── main/
│   ├── java/
│   │   └── com/GestionTenimba/gestionDeProduit/
│   │       ├── controller/
│   │       ├── modele/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       ├── static/
│       │   └── Images/
│       └── templates/
```

## API Endpoints

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | / | Page d'accueil |
| GET | /listeAvecCon | Liste des produits (utilisateurs authentifiés) |
| GET | /listeSansCon | Liste des produits (accès public) |
| POST | /create | Création d'un nouveau produit |
| POST | /create/{id} | Modification d'un produit existant |
| GET | /delete/{id} | Suppression d'un produit |
| POST | /recherche | Recherche de produits |

## Configuration de Sécurité

Identifiants par défaut :
- Utilisateur : `admin`
- Mot de passe : `admin`

