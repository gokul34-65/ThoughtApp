package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Comment;
import com.gokul.springdatajpatablesampleproject.model.Post;
import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.model.UserDTO;
import com.gokul.springdatajpatablesampleproject.repository.CommentRepository;
import com.gokul.springdatajpatablesampleproject.repository.PostRepository;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Util {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public UserDTO getUserDTOByUsername(String username){
        UserDTO userDTO = new UserDTO();
        User user = userRepository.findByUsername(username);
        userDTO.setDisplayName(user.getDisplayName());
        userDTO.setBio(user.getBio());
        userDTO.setLocation(user.getLocation());
        return userDTO;
    }

    public UserDTO getCurrentUserDTO(HttpServletRequest request) {
        String username = getUsernameFromRequest(request);
        return getUserDTOByUsername(username);
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtUtil.extractUserName(token);
        return username;
    }

    public boolean postExists(Long postId) {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if(post.getId().equals(postId)) {
                return true;
            }
        }
        return false;
    }

    public boolean commentExists(Long commentId) {
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            if(comment.getId().equals(commentId)) {
                return true;
            }
        }
        return false;
    }

    public boolean userExists(String username) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
