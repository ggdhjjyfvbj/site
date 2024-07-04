package com.example.demo.controller;

import com.example.demo.entity.NewsEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.dtos.NewsEntityDTO;
import com.example.demo.entity.forums.ForumEntity;
import com.example.demo.repostiory.NewsRepository;
import com.example.demo.utils.accountUtils.UserEntityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static com.example.demo.utils.accountUtils.UserEntityUtils.user;

@Controller
public class NewsController {


    @Autowired
    NewsRepository newsRepository;

    public static Date date = new Date();

    @GetMapping("/news")
    public String news(@ModelAttribute("entity") NewsEntity entity, Model model) {
        model.addAttribute("authenticatedUser", user());
        model.addAttribute("news", newsRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().limit(6).toList());
        if (!newsRepository.findAll().isEmpty())
            model.addAttribute("lastNews", newsRepository.findById((long) newsRepository.findAll().size()).get());
        model.addAttribute("images", newsRepository.findAll());
        model.addAttribute("date", date);
        return "/assets/pages/header-pages/news";
    }

    @PostMapping("/news")
    public String newsPost(@ModelAttribute("entity") NewsEntity entity, Model model) {
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/header-pages/news";
    }

    @GetMapping("/news/search")
    public String newsSearch(@ModelAttribute("searchEntity") NewsEntity entity, Model model) {
        model.addAttribute("authenticatedUser", user());
        List<NewsEntity> forum1 = newsRepository.findByTitleStartsWithIgnoreCaseOrderByTitle(entity.getTitle());
        if (forum1.isEmpty()) {
            return "redirect:/welcome";
        }
        model.addAttribute("searchForm",forum1);
        return "/assets/pages/header-pages/news-search";
    }

    @PostMapping("/news/search")
    public String newsSearchPost(@ModelAttribute("searchEntity") NewsEntity entity, Model model) {
        model.addAttribute("authenticatedUser", user());
        List<NewsEntity> forum1 = newsRepository.findByTitleStartsWithIgnoreCaseOrderByTitle(entity.getTitle());
        if (forum1.isEmpty()) {
            return "redirect:/welcome";
        }
        model.addAttribute("searchForm",forum1);
        return "/assets/pages/header-pages/news-search";
    }

    @GetMapping("/create-news")
    public String create(Model model, @ModelAttribute(name = "dto") NewsEntityDTO dto) {
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/admin/create-news";
    }

    @PostMapping("/create-news")
    public String createNews(Model model, @Valid @ModelAttribute(name = "dto") NewsEntityDTO dto, @RequestParam("file") MultipartFile file, BindingResult bindingResult)  {
        UUID uuid = UUID.randomUUID();
        NewsEntity news = new NewsEntity();
        String originalNameFile = uuid + file.getOriginalFilename();

        if (bindingResult.hasErrors()) {
            return "/assets/pages/admin/create-news";
        }

        try {
            String directory = "C:\\projects\\demo3\\src\\main\\resources\\static\\imgs\\";
            Path searchDirectory = Paths.get(directory);

            if (!Files.exists(searchDirectory)) {
                Files.createDirectory(searchDirectory);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(directory + originalNameFile), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            Logger.getLogger("ERROR", e.getMessage());
        }

        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setDate(date);
        news.setFilename(originalNameFile);
        newsRepository.save(news);
        return "redirect:/welcome";
    }

    @GetMapping("news/more")
    public String moreNews(Model model, @RequestParam("id") long id) {
        NewsEntity user = newsRepository.findById(id).get();
        model.addAttribute("userr", user);
        model.addAttribute("authenticatedUser", user());

        return "/assets/pages/header-pages/news-more";
    }


}
