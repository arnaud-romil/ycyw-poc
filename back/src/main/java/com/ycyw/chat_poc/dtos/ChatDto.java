package com.ycyw.chat_poc.dtos;

import com.ycyw.chat_poc.models.Chat;
import java.time.Instant;
import lombok.Getter;

@Getter
public class ChatDto {

  public ChatDto(Chat chat) {
    this.id = chat.getId();
    this.customer = chat.getCustomer().getEmail();
    this.createdAt = chat.getCreatedAt();
  }

  private Long id;
  private String customer;
  private Instant createdAt;
}
