package com.ycyw.chat_poc.services;

import com.ycyw.chat_poc.models.Chat;
import com.ycyw.chat_poc.models.User;
import com.ycyw.chat_poc.repositories.ChatRepository;
import java.time.Instant;
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
}
