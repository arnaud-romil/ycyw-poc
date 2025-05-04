package com.ycyw.chat_poc.repositories;

import com.ycyw.chat_poc.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {}
