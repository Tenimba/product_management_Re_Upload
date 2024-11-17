package com.GestionTenimba.gestionDeProduit.controller;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.GestionTenimba.gestionDeProduit.TestSecurityConfig;
import com.GestionTenimba.gestionDeProduit.modele.Produit;
import com.GestionTenimba.gestionDeProduit.service.ProduitService;
@WebMvcTest(ProduitController.class)
@Import(TestSecurityConfig.class)
class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduitService produitService;

    private Produit produit1;
    private Produit produit2;
    private Page<Produit> mockPage;

    @BeforeEach
    void setUp() {
        produit1 = new Produit();
        produit1.setId(1);
        produit1.setNom("Produit 1");
        produit1.setPrix(100.0);
        produit1.setDevise("EUR");
        produit1.setTaxe(20);
        produit1.setDateExpiration(LocalDate.now().plusDays(30));
        produit1.setFournisseur("Fournisseur 1");
        produit1.setImage("produit1.jpg");

        produit2 = new Produit();
        produit2.setId(2);
        produit2.setNom("Produit 2");
        produit2.setPrix(200.0);
        produit2.setDevise("USD");
        produit2.setTaxe(30);
        produit2.setDateExpiration(LocalDate.now().plusDays(60));
        produit2.setFournisseur("Fournisseur 2");
        produit2.setImage("produit2.jpg");

        mockPage = new PageImpl<>(
            Arrays.asList(produit1, produit2),
            PageRequest.of(0, 4),
            2
        );
    }

    @Test
    @WithMockUser
    void testListeSansCon() throws Exception {
        when(produitService.lireProduitsPagine(0, 4, "id")).thenReturn(mockPage);

        mockMvc.perform(get("/listeSansCon")
                .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("list"))
               .andExpect(model().attributeExists("produits"));
    }

    @Test
    @WithMockUser
    void testCreateProduit() throws Exception {
        when(produitService.create(any(Produit.class))).thenReturn(produit1);
        when(produitService.lireProduitsPagine(0, 4, "id")).thenReturn(mockPage);

        mockMvc.perform(post("/create")
                .with(csrf())
                .param("nom", "Produit 1")
                .param("prix", "100.0")
                .param("devise", "EUR")
                .param("taxe", "20")
                .param("dateExpiration", LocalDate.now().plusDays(30).toString())
                .param("fournisseur", "Fournisseur 1"))
               .andExpect(status().isOk())
               .andExpect(view().name("list"))
               .andExpect(model().attributeExists("produits"));
    }

    @Test
    @WithMockUser
    void testUpdateProduit() throws Exception {
        when(produitService.byId(1)).thenReturn(produit1);
        when(produitService.update(eq(1), any(Produit.class))).thenReturn(produit1);
        when(produitService.lireProduitsPagine(0, 4, "id")).thenReturn(mockPage);

        mockMvc.perform(post("/create/1")
                .with(csrf())
                .param("id", "1")
                .param("nom", "Produit 1 Updated")
                .param("prix", "150.0")
                .param("devise", "EUR")
                .param("taxe", "25")
                .param("dateExpiration", LocalDate.now().plusDays(45).toString())
                .param("fournisseur", "Fournisseur 1"))
               .andExpect(status().isOk())
               .andExpect(view().name("list"))
               .andExpect(model().attributeExists("produits"));
    }


    @Test
    @WithMockUser
    void testRechercheProduits() throws Exception {
        when(produitService.search("Produit 1", "nom")).thenReturn(mockPage);

        mockMvc.perform(post("/recherche")
                .with(csrf())
                .param("recherche", "Produit 1")
                .param("critere", "nom"))
               .andExpect(status().isOk())
               .andExpect(view().name("list"))
               .andExpect(model().attributeExists("produits"));
    }
}