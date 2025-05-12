package com.ycyw.chat_poc.repositories;

import com.ycyw.chat_poc.models.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
