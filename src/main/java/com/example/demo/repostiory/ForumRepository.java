package com.example.demo.repostiory;

import com.example.demo.entity.forums.ForumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForumRepository extends JpaRepository<ForumEntity, Long> {

    List<ForumEntity> findByTitleStartsWithIgnoreCaseOrderByTitle(String title);
}
