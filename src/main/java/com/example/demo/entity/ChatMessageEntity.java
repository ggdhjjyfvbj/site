package com.example.demo.entity;


import com.example.demo.entity.forums.ForumEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = Integer.MAX_VALUE)
    public String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ForumEntity forum;
}
