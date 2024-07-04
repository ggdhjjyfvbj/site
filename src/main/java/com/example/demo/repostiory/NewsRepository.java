package com.example.demo.repostiory;

import com.example.demo.entity.NewsEntity;
import com.example.demo.entity.forums.ForumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    List<NewsEntity> findByTitleStartsWithIgnoreCaseOrderByTitle(String title);
}
