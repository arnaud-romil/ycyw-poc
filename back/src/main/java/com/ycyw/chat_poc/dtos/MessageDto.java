package com.ycyw.chat_poc.dtos;

import com.ycyw.chat_poc.models.Message;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {

  private String sender;
  private String content;
  private Instant createdAt;

  public MessageDto(Message message) {
    this.sender = message.getSender().getFirstName() + " " + message.getSender().getLastName();
    this.content = message.getContent();
    this.createdAt = message.getCreatedAt();
  }
}
