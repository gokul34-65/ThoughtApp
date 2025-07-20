package com.gokul.springdatajpatablesampleproject.repository;

import com.gokul.springdatajpatablesampleproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
