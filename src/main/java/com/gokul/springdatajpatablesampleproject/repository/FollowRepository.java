package com.gokul.springdatajpatablesampleproject.repository;


import com.gokul.springdatajpatablesampleproject.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findByFollowing(String following);
}
