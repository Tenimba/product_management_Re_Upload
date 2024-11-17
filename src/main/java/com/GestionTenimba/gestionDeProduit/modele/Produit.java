package com.GestionTenimba.gestionDeProduit.modele;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUITS")
@Getter
@Setter
@NoArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    private double prix;

    @Column(length = 3)
    private String devise;

    private Integer taxe;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateExpiration;

    private String fournisseur;

    private String image;

    @Transient
    private MultipartFile imageFile;
}