package com.ycyw.chat_poc.dtos;

import com.ycyw.chat_poc.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

  private String email;
  private String firstName;
  private String lastName;
  private String role;

  public UserDto(User user) {
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.role = user.getRole().name();
  }
}
