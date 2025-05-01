package com.ycyw.chat_poc.services;

import com.ycyw.chat_poc.exceptions.UserUnauthorizedException;
import com.ycyw.chat_poc.models.User;
import com.ycyw.chat_poc.payloads.LoginRequest;
import com.ycyw.chat_poc.payloads.LoginResponse;
import com.ycyw.chat_poc.repositories.UserRepository;
import com.ycyw.chat_poc.utils.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public LoginResponse login(LoginRequest loginRequest) {

    Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

    if (userOptional.isEmpty()
        || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
      throw new UserUnauthorizedException("Invalid email or password");
    }

    return new LoginResponse(jwtUtil.generateAccessToken(userOptional.get()));
  }
}
