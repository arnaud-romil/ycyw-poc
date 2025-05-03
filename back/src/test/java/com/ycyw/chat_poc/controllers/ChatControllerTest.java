package com.ycyw.chat_poc.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycyw.chat_poc.payloads.LoginResponse;
import com.ycyw.chat_poc.repositories.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ChatControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ChatRepository chatRepository;

  @Test
  @DirtiesContext
  void shouldBeAbleToCreateChat() throws Exception {
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

    mockMvc
        .perform(post("/chats").header("Authorization", "Bearer " + loginResponse.getAccessToken()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.customer").value("customer@test.com"))
        .andExpect(jsonPath("$.createdAt").exists());

    assertEquals(2L, chatRepository.count());
  }

  @Test
  void shouldNotBeAbleToCreateChatWithLogin() throws Exception {
    final String loginRequest =
        """
                 {
                     "email": "agent1@ycyw.com",
                     "password": "user2Password!"
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

    mockMvc
        .perform(post("/chats").header("Authorization", "Bearer " + loginResponse.getAccessToken()))
        .andExpect(status().isForbidden());

    assertEquals(1L, chatRepository.count());
  }

  @Test
  void shouldBeAbleToViewChats() throws Exception {

    final String loginRequest =
        """
                 {
                     "email": "agent1@ycyw.com",
                     "password": "user2Password!"
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

    mockMvc
        .perform(get("/chats").header("Authorization", "Bearer " + loginResponse.getAccessToken()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.chats.length()").value(1))
        .andExpect(jsonPath("$.chats[0].customer").value("customer@test.com"));
  }

  @Test
  void shouldNotBeAbleToViewChats() throws Exception {

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

    mockMvc
        .perform(get("/chats").header("Authorization", "Bearer " + loginResponse.getAccessToken()))
        .andExpect(status().isForbidden());
  }
}
