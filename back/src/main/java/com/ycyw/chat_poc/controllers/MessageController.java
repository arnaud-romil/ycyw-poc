package com.ycyw.chat_poc.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

  @MessageMapping("/chat.send.{chatId}")
  @SendTo("/topic/chat.{chatId}")
  public String sendMessage(@DestinationVariable String chatId, String message) {
    System.out.println(message + " => " + chatId);
    return message;
  }
}
