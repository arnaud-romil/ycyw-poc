package com.ycyw.chat_poc.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycyw.chat_poc.payloads.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void loginAsCustomer() throws Exception {

    final String loginRequest =
        """
                 {
                     "email": "customer@test.com",
                     "password": "user1Password!"
                  }
                """;

    MvcResult result =
        mockMvc
            .perform(
                post("/auth/login").content(loginRequest).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andReturn();

    LoginResponse loginResponse =
        objectMapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class);

    System.out.println("Access Token: " + loginResponse.getAccessToken());
  }

  @Test
  void loginAsCustomerService() throws Exception {

    final String loginRequest =
        """
                 {
                     "email": "agent1@ycyw.com",
                     "password": "user2Password!"
                  }
                """;

    mockMvc
        .perform(post("/auth/login").content(loginRequest).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").exists())
        .andReturn();
  }

  @Test
  void loginWithWrongPassword() throws Exception {

    final String loginRequest =
        """
                 {
                     "email": "customer@test.com",
                     "password": "wrongPassword!"
                  }
                """;

    mockMvc
        .perform(post("/auth/login").content(loginRequest).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.accessToken").doesNotExist());
  }
}
