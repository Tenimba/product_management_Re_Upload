package com.GestionTenimba.gestionDeProduit.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.GestionTenimba.gestionDeProduit.modele.Produit;
import com.GestionTenimba.gestionDeProduit.repository.ProduitRepository;

class ProduitServiceImplTest {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitServiceImpl produitService;

    private Produit produit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produit = new Produit();
        produit.setId(1);
        produit.setNom("Test Produit");
        produit.setPrix(100.0);
        produit.setDevise("EUR");
        produit.setTaxe(20);
        produit.setDateExpiration(LocalDate.now().plusDays(30));
        produit.setFournisseur("Test Fournisseur");
        produit.setImage("test.jpg");
    }

    @Test
    void testCreateProduit() {
        when(produitRepository.save(produit)).thenReturn(produit);
        Produit created = produitService.create(produit);
        assertNotNull(created);
        assertEquals("Test Produit", created.getNom());
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testReadProduitById() {
        when(produitRepository.findById(1)).thenReturn(Optional.of(produit));
        Produit found = produitService.byId(1);
        assertNotNull(found);
        assertEquals("Test Produit", found.getNom());
        verify(produitRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateProduit() {
        when(produitRepository.findById(1)).thenReturn(Optional.of(produit));
        when(produitRepository.save(produit)).thenReturn(produit);
        
        produit.setPrix(150.0);
        Produit updated = produitService.update(1, produit);
        
        assertNotNull(updated);
        assertEquals(150.0, updated.getPrix());
        verify(produitRepository, times(1)).findById(1);
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testDeleteProduit() {
        doNothing().when(produitRepository).deleteById(1);
        produitService.delete(1);
        verify(produitRepository, times(1)).deleteById(1);
    }
}