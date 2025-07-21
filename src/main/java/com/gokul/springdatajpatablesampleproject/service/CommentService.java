package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Comment;
import com.gokul.springdatajpatablesampleproject.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<List<Comment>> getComment(Long postId) {
        return new ResponseEntity<>(commentRepository.findByPostId(postId),HttpStatus.OK);
    }

    public ResponseEntity<String> updateComment(HttpServletRequest request, Long commentId, Comment comment) {
        String username = util.getUsernameFromRequest(request);
        if(!util.commentExists(commentId)){
            return new ResponseEntity<>("Requested comment not found",HttpStatus.BAD_REQUEST);
        }
        Comment comment1 = commentRepository.findById(commentId).get();
        if(!comment1.getUserName().equals(username)){
            return new ResponseEntity<>("Not authorized",HttpStatus.UNAUTHORIZED);
        }
        comment1.setContent(comment.getContent());
        commentRepository.save(comment1);
        return new ResponseEntity<>("Comment updated successfully",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteComment(HttpServletRequest request, Long commentId) {
        String username = util.getUsernameFromRequest(request);
        if(!util.commentExists(commentId)){
            return new ResponseEntity<>("Requested comment not found",HttpStatus.BAD_REQUEST);
        }
        Comment comment1 = commentRepository.findById(commentId).get();
        if(!comment1.getUserName().equals(username)){
            return new ResponseEntity<>("Not authorized",HttpStatus.UNAUTHORIZED);
        }
       commentRepository.delete(comment1);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
