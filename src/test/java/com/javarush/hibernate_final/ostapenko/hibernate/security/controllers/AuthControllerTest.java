package com.javarush.hibernate_final.ostapenko.hibernate.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.hibernate_final.ostapenko.hibernate.controller.quests.QuestsUIController;
import com.javarush.hibernate_final.ostapenko.hibernate.security.controller.AuthController;
import com.javarush.hibernate_final.ostapenko.hibernate.security.dto.AuthRequest;
import com.javarush.hibernate_final.ostapenko.hibernate.security.jwt.JwtService;
import com.javarush.hibernate_final.ostapenko.hibernate.service.QuestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void createAuthToken_whenValidInput_thenReturn200() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin123");

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authRequest))
        ).andExpect(status().isOk());

    }

    @Test
    void createAuthToken_whenWrongUser_thenReturn401() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("test222");
        authRequest.setPassword("admin123");

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authRequest))
        ).andExpect(status().isUnauthorized());
    }


}
