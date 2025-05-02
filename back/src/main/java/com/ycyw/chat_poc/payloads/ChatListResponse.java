package com.ycyw.chat_poc.payloads;

import com.ycyw.chat_poc.dtos.ChatDto;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChatListResponse {

  private final List<ChatDto> chats;
}
