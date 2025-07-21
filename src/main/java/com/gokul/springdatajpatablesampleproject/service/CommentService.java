package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Comment;
import com.gokul.springdatajpatablesampleproject.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    Util util;
    @Autowired
    CommentRepository commentRepository;
    public ResponseEntity<String> addComment(HttpServletRequest request, Long postId, Comment comment) {
        String username = util.getUsernameFromRequest(request);
        if(!util.postExists(postId)){
            return new ResponseEntity<>("Requested post to comment not found",HttpStatus.BAD_REQUEST);
        }
        Comment newcomment =  new Comment();
        newcomment.setContent(comment.getContent());
        newcomment.setUserName(username);
        newcomment.setPostId(postId);
        commentRepository.save(newcomment);
        return new ResponseEntity<>("Comment added successfully",HttpStatus.CREATED);
    }
}
