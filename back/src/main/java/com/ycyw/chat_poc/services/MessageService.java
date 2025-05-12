package com.ycyw.chat_poc.services;

import com.ycyw.chat_poc.dtos.MessageDto;
import com.ycyw.chat_poc.models.Message;
import com.ycyw.chat_poc.repositories.MessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository messageRepository;

  public void saveMessage(Message message) {
    messageRepository.save(message);
  }

  public List<MessageDto> getChatMessages(Long chatId) {
    return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId).stream()
        .map(MessageDto::new)
        .toList();
  }
}
