package com.gokul.springdatajpatablesampleproject.repository;


import com.gokul.springdatajpatablesampleproject.model.Post;
import com.gokul.springdatajpatablesampleproject.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarRepository extends JpaRepository<Star,Long> {
    List<Star> findByPostId(Long id);

    List<Star> findByUsername(String currentUserName);
}
