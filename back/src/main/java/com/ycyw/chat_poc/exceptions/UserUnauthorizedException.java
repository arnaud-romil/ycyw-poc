package com.ycyw.chat_poc.exceptions;

public class UserUnauthorizedException extends RuntimeException {

  public UserUnauthorizedException(String message) {
    super(message);
  }
}
