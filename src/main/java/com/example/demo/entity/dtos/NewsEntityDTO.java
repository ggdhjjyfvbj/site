package com.example.demo.entity.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class NewsEntityDTO {

    @NotBlank(message = "Это обязательное поле")
    private String title;



    @NotBlank(message = "Это обязательное поле")
    private String description;


}
