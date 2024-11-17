package com.GestionTenimba.gestionDeProduit.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.GestionTenimba.gestionDeProduit.modele.Produit;
import com.GestionTenimba.gestionDeProduit.repository.ProduitRepository;

class ProduitServiceTest {

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
    }

    @Test
    void testListProduits() {
        when(produitRepository.findAll()).thenReturn(List.of(produit));
        List<Produit> produits = produitService.list();
        assertNotNull(produits);
        assertEquals(1, produits.size());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    void testFindProduitById() {
        when(produitRepository.findById(1)).thenReturn(Optional.of(produit));
        Produit found = produitService.byId(1);
        assertNotNull(found);
        assertEquals("Test Produit", found.getNom());
        verify(produitRepository, times(1)).findById(1);
    }
}