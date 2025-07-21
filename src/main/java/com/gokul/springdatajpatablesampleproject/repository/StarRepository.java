package com.gokul.springdatajpatablesampleproject.repository;


import com.gokul.springdatajpatablesampleproject.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarRepository extends JpaRepository<Star,Long> {
}
