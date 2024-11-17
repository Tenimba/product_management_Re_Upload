package com.GestionTenimba.gestionDeProduit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.GestionTenimba.gestionDeProduit.modele.User;
import com.GestionTenimba.gestionDeProduit.repository.UserRepository;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setPrenom("Prenom");
        user.setNom("Nom");
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(user);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser@example.com");
        assertNotNull(userDetails);
        assertEquals("testuser@example.com", userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail("testuser@example.com");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown@example.com");
        });
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }
}