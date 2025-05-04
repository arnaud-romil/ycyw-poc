package com.ycyw.chat_poc.services;

import com.ycyw.chat_poc.dtos.ChatDto;
import com.ycyw.chat_poc.models.Chat;
import com.ycyw.chat_poc.models.User;
import com.ycyw.chat_poc.repositories.ChatRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRepository chatRepository;

  public Chat createChat(User customer) {
    Chat chat = new Chat();
    chat.setCustomer(customer);
    chat.setCreatedAt(Instant.now());
    return chatRepository.save(chat);
  }

  public List<ChatDto> getChats() {
    return chatRepository.findAll().stream().map(ChatDto::new).toList();
  }

  public Chat findyById(Long id) {
    return chatRepository.findById(id).orElseThrow(() -> new RuntimeException("Chat not found"));
  }
}
