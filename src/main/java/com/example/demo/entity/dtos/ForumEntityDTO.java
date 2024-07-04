package com.example.demo.entity.dtos;

import com.example.demo.entity.UserEntity;
import com.example.demo.entity.forums.ForumCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class ForumEntityDTO {


    @NotBlank(message = "Это обязательное поле")
    @Size(max = 200, message = "Максимум 200 символов")
    private String title;


    @NotBlank(message = "Это обязательное поле")
    @Size(max = 10000, message = "Максимум 10000 символов")
    private String description;

    private Date date;

    private String answers;

    private String views;

    private ForumCategory category;

    private String filename;

    private UserEntity user;
}
