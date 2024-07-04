package com.example.demo.controller;

import com.example.demo.entity.ChatMessageEntity;
import com.example.demo.entity.dtos.ForumEntityDTO;
import com.example.demo.entity.forums.ForumEntity;
import com.example.demo.repostiory.ForumRepository;
import com.example.demo.repostiory.MessageRepository;
import com.example.demo.repostiory.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Logger;

import static com.example.demo.utils.accountUtils.UserEntityUtils.user;

@Controller
public class ForumController {

    @Autowired
    ForumRepository forumRepository;

    @Autowired
    UserRepository userRepository;



    @Autowired
    MessageRepository messageRepository;

    static int sum =0;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("authenticatedUser", user());
        return "index";
    }

    @GetMapping("/forum")
    public String forumPage(@ModelAttribute("forumsssssss") ForumEntity forum, Model model) {
        model.addAttribute("authenticatedUser", user());
        model.addAttribute("forums", forumRepository.findAll());
        return "/assets/pages/forum/forum";
    }

    @PostMapping("/forum/")
    public String se2arch(@ModelAttribute("forumsssssss") ForumEntity forum, Model model) {
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/forum/forum";
    }


    @GetMapping("/forum/search")
    public String s2earch(@ModelAttribute("forum") ForumEntity forum, Model model) {

        model.addAttribute("authenticatedUser", user());
        List<ForumEntity> forum1 = forumRepository.findByTitleStartsWithIgnoreCaseOrderByTitle(forum.getTitle());
        if (forum1.isEmpty()) {
            return "redirect:/welcome";
        }
        model.addAttribute("forumsss",forum1);
        return "/assets/pages/forum/search";
    }

    @PostMapping("/forum/search")
    public String search(@ModelAttribute("forum") ForumEntity forum, Model model) {
        model.addAttribute("authenticatedUser", user());
        List<ForumEntity> forum1 = forumRepository.findByTitleStartsWithIgnoreCaseOrderByTitle(forum.getTitle());
        if (forum1.isEmpty()) {
            return "redirect:/welcome";
        }
        model.addAttribute("forumsss",forum1);
        return "/assets/pages/forum/search";
    }

    @GetMapping("/create-topic")
    public String forumPages(Model model, @ModelAttribute(name = "forumEntity") ForumEntityDTO forumEntity) {
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/forum/createTopic";
    }



    @PostMapping("/create-topic")
    public String createForum(@Valid @ModelAttribute(name = "forumEntity") ForumEntityDTO forumEntity,
                              BindingResult bindingResult, @RequestParam MultipartFile files) {
        String filename = UUID.randomUUID() + files.getOriginalFilename();
        if (bindingResult.hasErrors()) {
             return "/assets/pages/forum/createTopic";
        }
        try {
            String directory = "C:\\projects\\demo3\\src\\main\\resources\\static\\imgs\\";
            Path searchDirectory = Paths.get(directory);

            if (!Files.exists(searchDirectory)) {
                Files.createDirectory(searchDirectory);
            }
            try (InputStream inputStream = files.getInputStream()) {
                Files.copy(inputStream, Paths.get(directory + filename), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            Logger.getLogger("ERROR", e.getMessage());
        }
        ForumEntity forum = new ForumEntity();
        forum.setDate(new Date());
        forum.setTitle(forumEntity.getTitle());
        forum.setDescription(forumEntity.getDescription());
        forum.setCategory(forumEntity.getCategory());
        forum.setFilename(filename);
        forum.setUserEntity(user());
        forumRepository.save(forum);
        return "redirect:/forum";
    }

    @GetMapping("/forum/read")
    public String readForum(@RequestParam long id, Model model) {
        ForumEntity forum = forumRepository.findById(id).get();
        model.addAttribute("authenticatedUser", user());
        model.addAttribute("forumRead", forum);
        return "/assets/pages/forum/forum-read";
    }

    @GetMapping("/forum/comment")
    public String comment(@RequestParam long id, Model model) {
        ForumEntity forum = forumRepository.findById(id).get();
        model.addAttribute("comment", forum);
        model.addAttribute("forumComment", messageRepository.findAll().stream().filter(form -> form.getForum().getId() == id).toList());
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/forum/comment";
    }

    @PostMapping("/forum/comment")
    public String postComment(@RequestParam long id,ChatMessageEntity chatMessage) {
        ForumEntity forum = forumRepository.findById(id).get();
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setForum(forum);
        chatMessageEntity.setUser(user());
        chatMessageEntity.setContent(chatMessage.getContent());
        messageRepository.save(chatMessageEntity);
        String url = "/forum/comment" + "?id=" + id;
        return "redirect:" + url;
    }
}
