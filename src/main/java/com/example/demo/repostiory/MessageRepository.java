package com.example.demo.repostiory;

import com.example.demo.entity.ChatMessageEntity;
import com.example.demo.entity.forums.ForumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
