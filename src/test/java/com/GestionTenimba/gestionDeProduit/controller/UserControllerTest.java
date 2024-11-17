package com.GestionTenimba.gestionDeProduit.controller;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.GestionTenimba.gestionDeProduit.TestSecurityConfig;
import com.GestionTenimba.gestionDeProduit.modele.User;
import com.GestionTenimba.gestionDeProduit.repository.UserRepository;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setPrenom("Prenom1");
        user1.setNom("Nom1");

        user2 = new User();
        user2.setId(2L);
        user2.setEmail("admin@example.com");
        user2.setPassword("password2");
        user2.setPrenom("Prenom2");
        user2.setNom("Nom2");
    }

    @Test
    @WithMockUser
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register").with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("createUser"));
    }

    @Test
    @WithMockUser
    void testProcessRegister() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/process_register")
                .with(csrf())
                .param("email", "test@test.com")
                .param("password", "password")
                .param("prenom", "Test")
                .param("nom", "User"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testListUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users").with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("users"));
    }
}
