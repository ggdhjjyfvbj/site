package com.example.demo.entity.forums;

import com.example.demo.entity.ChatMessageEntity;
import com.example.demo.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity

public class ForumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 200)
    @NotBlank(message = "Это обязательное поле")
    @Size(max = 200, message = "Максимум 200 символов")
    private String title;

    @Column(length = 10000)
    @NotBlank(message = "Это обязательное поле")
    @Size(max = 10000, message = "Максимум 10000 символов")
    private String description;

    @DateTimeFormat(pattern = "yyyy-dd-mm")
    private Date date;

    private int answers;


    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity userEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forum")
    public List<ChatMessageEntity> chatMessageEntities;



    public long getId() {
        return id;
    }

    public List<ChatMessageEntity> getChatMessageEntities() {
        return chatMessageEntities;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ForumCategory getCategory() {
        return category;
    }

    public void setCategory(ForumCategory category) {
        this.category = category;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private int views;

    private ForumCategory category;

    private String filename;

}
