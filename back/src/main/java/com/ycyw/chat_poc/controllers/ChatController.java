package com.ycyw.chat_poc.controllers;

import com.ycyw.chat_poc.dtos.ChatDto;
import com.ycyw.chat_poc.dtos.MessageDto;
import com.ycyw.chat_poc.models.User;
import com.ycyw.chat_poc.services.ChatService;
import com.ycyw.chat_poc.services.MessageService;
import com.ycyw.chat_poc.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;
  private final UserService userService;
  private final MessageService messageService;

  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping
  public ResponseEntity<ChatDto> createChat(Authentication authentication) {
    User customer = userService.findByEmail(authentication.getName());
    ChatDto chatDto = new ChatDto(chatService.createChat(customer));
    return ResponseEntity.ok(chatDto);
  }

  @PreAuthorize("hasRole('SUPPORT')")
  @GetMapping
  public ResponseEntity<List<ChatDto>> getChats() {
    return ResponseEntity.ok(chatService.getChats());
  }

  @GetMapping("/{id}/messages")
  public ResponseEntity<List<MessageDto>> getChatMessages(@PathVariable Long id) {
    return ResponseEntity.ok(messageService.getChatMessages(id));
  }
}
