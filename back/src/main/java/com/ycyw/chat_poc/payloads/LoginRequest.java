package com.ycyw.chat_poc.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  private String email;
  private String password;
}
