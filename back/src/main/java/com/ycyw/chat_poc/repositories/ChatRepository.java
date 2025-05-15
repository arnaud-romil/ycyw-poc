package com.ycyw.chat_poc.repositories;

import com.ycyw.chat_poc.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {}
