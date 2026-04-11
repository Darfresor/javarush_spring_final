package com.javarush.hibernate_final.ostapenko.hibernate.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.hibernate_final.ostapenko.hibernate.controller.quests.QuestsUIController;
import com.javarush.hibernate_final.ostapenko.hibernate.security.controller.AuthController;
import com.javarush.hibernate_final.ostapenko.hibernate.security.controller.AuthControllerExceptionHandler;
import com.javarush.hibernate_final.ostapenko.hibernate.security.dto.AuthRequest;
import com.javarush.hibernate_final.ostapenko.hibernate.security.dto.AuthResponse;
import com.javarush.hibernate_final.ostapenko.hibernate.security.jwt.JwtService;
import com.javarush.hibernate_final.ostapenko.hibernate.service.QuestService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static com.javarush.hibernate_final.ostapenko.hibernate.security.controller.AuthControllerExceptionHandler.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {


    /*
1. Прослушивание ввода
2. Десериализация ввода(path variable, requestParam вот эти аннотации за это отвечают)
3. Проверка ввода( пришло ли все что пришло в валидной форме.
Нужный формат, диапазон значений и т.п. В том числе есть есть аннотации самого спринга для проверки DTO через анотации. Вроде не null и т.п.)
4. Запуск бизнес-логики
5. Сериализация вывода(когда возвращает по итогу например готовую веб-страницу)
6. Перевод исключений
    * */

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;





    /* Проверяем этап №2 десериализацию ввода - успех + №4 запуск бизнес-логики*/
    @Test
    void createAuthToken_whenValidInput_thenReturn200() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin123");

        UserDetails userDetails = new User("admin", "admin123", new ArrayList<>());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //при любом вызове аутентификации возвращаем успех
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        //настраиваем генерацию токена
        when(jwtService.generateToken(userDetails)).thenReturn("test.jwt.token");



        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authRequest))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test.jwt.token"));

    }

    /* Проверяем этап №2 десериализацию ввода - ошибка + №3 проверка ввода */
    @Test
    void createAuthToken_whenWrongUser_thenReturn400() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("test222");
        authRequest.setPassword("ad");

        //настраиваем чтобы аутентификация бросала ошибку при проверке данных пользователя
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authRequest))
        ).andExpect(status().isBadRequest());
    }

    /* Проверяем этап №3 проверка ввода - ошибка*/
    @Test
    void createAuthToken_whenWrongUser_thenReturn400AndErrorsResult() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("test222");
        authRequest.setPassword("ad");

        //настраиваем чтобы аутентификация бросала ошибку при проверке данных пользователя
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        MvcResult mvcResult =  mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authRequest))
                ).andExpect(status().isBadRequest())
                .andReturn();
        ErrorResult expectedErrorResponse =  new ErrorResult("password","Password must be at least 3 characters long");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedErrorResponse);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);

    }
    /* Этап 2 и 3 */
    @Test
    void createAuthToken_whenValidInput_shouldAuthenticateWithCorrectCredentials() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin123");

        UserDetails userDetails = new User("admin", "admin123", new ArrayList<>());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //при любом вызове аутентификации возвращаем успех
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        //настраиваем генерацию токена
        when(jwtService.generateToken(userDetails)).thenReturn("test.jwt.token");

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authRequest))
        ).andExpect(status().isOk());

        // Создаем ArgumentCaptor для проверки аргументов переданных в моки
        ArgumentCaptor<UsernamePasswordAuthenticationToken> argumentCaptor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        // Проверяем, что authenticate был вызван ровно 1 раз
        verify(authenticationManager, times(1))
                .authenticate(argumentCaptor.capture());

        // Проверяем, что в authenticate были переданы правильные credentials
        UsernamePasswordAuthenticationToken capturedArgument = argumentCaptor.getValue();
        assertEquals("admin", capturedArgument.getPrincipal());
        assertEquals("admin123", capturedArgument.getCredentials());

        // Проверяем, что generateToken был вызван ровно 1 раз с правильным параметром
        verify(jwtService, times(1))
                .generateToken(userDetails);


    }


    /* Проверяем этап №4 работа бизнес логики + перевод исключений*/
    @Test
    void createAuthToken_whenWrongUser_thenReturn401() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("test222");
        authRequest.setPassword("admin123");

        //настраиваем чтобы аутентификация бросала ошибку при проверке данных пользователя
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authRequest))
                ).andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    /* Проверяем этап№4 бизнес-логика */
    @Test
    void createAuthToken_whenValidInput_thenReturnAuthResponse() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin123");

        UserDetails userDetails = new User("admin", "admin123", new ArrayList<>());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //при любом вызове аутентификации возвращаем успех
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        //настраиваем генерацию токена
        when(jwtService.generateToken(userDetails)).thenReturn("test.jwt.token");



        MvcResult mvcResult =  mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authRequest))
        ).andReturn();
        String expectedResponseBody = objectMapper.writeValueAsString(new AuthResponse("test.jwt.token"));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);

    }








}
