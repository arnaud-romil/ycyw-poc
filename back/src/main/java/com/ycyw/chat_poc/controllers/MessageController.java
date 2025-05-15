package com.ycyw.chat_poc.controllers;

import com.ycyw.chat_poc.dtos.MessageDto;
import com.ycyw.chat_poc.models.Chat;
import com.ycyw.chat_poc.models.Message;
import com.ycyw.chat_poc.models.User;
import com.ycyw.chat_poc.services.ChatService;
import com.ycyw.chat_poc.services.MessageService;
import com.ycyw.chat_poc.services.UserService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

  private final UserService userService;
  private final ChatService chatService;
  private final MessageService messageService;

  @MessageMapping("/chat/{chatId}")
  @SendTo("/topic/chat/{chatId}")
  public MessageDto sendMessage(@DestinationVariable Long chatId, MessageDto messageDto) {

    User sender = userService.findByEmail(messageDto.getSender());
    Chat chat = chatService.findyById(chatId);

    Message message = new Message();
    message.setSender(sender);
    message.setChat(chat);
    message.setContent(messageDto.getContent());
    message.setCreatedAt(Instant.now());

    messageService.saveMessage(message);

    messageDto.setSender(sender.getFirstName() + " " + sender.getLastName());
    return messageDto;
  }
}
