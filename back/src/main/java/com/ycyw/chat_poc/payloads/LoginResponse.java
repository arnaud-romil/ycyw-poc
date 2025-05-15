package com.ycyw.chat_poc.payloads;

import com.ycyw.chat_poc.dtos.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {

  private final String accessToken;
  private final UserDto user;
}
