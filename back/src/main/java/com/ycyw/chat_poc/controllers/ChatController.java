package com.ycyw.chat_poc.controllers;

import com.ycyw.chat_poc.models.User;
import com.ycyw.chat_poc.payloads.ChatListResponse;
import com.ycyw.chat_poc.services.ChatService;
import com.ycyw.chat_poc.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;
  private final UserService userService;

  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping
  public ResponseEntity<String> createChat(Authentication authentication) {
    User customer = userService.findByEmail(authentication.getName());
    chatService.createChat(customer);
    return ResponseEntity.ok("Chat created successfully!");
  }

  @PreAuthorize("hasRole('CUSTOMER_SERVICE')")
  @GetMapping
  public ResponseEntity<ChatListResponse> getChats() {
    return ResponseEntity.ok(new ChatListResponse(chatService.getChats()));
  }
}
