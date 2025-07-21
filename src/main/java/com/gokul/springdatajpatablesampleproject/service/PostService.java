package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Follow;
import com.gokul.springdatajpatablesampleproject.model.Post;
import com.gokul.springdatajpatablesampleproject.model.Star;
import com.gokul.springdatajpatablesampleproject.repository.FollowRepository;
import com.gokul.springdatajpatablesampleproject.repository.PostRepository;
import com.gokul.springdatajpatablesampleproject.repository.StarRepository;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    StarRepository starRepository;

    @Autowired
    Util util;

    public ResponseEntity<String> addPost(HttpServletRequest request, Post post){
        String username = util.getUsernameFromRequest(request);
        post.setUsername(username);//manually setting the username
        postRepository.save(post);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    public List<Post> getAllPostDTOs() {
        List<Post> postDTOs = new ArrayList<>();
        for(Post post : postRepository.findAll()){
            postDTOs.add(new Post(post.getId(),post.getContent(),post.getUsername()));
        }
         return postDTOs;
    }

    public ResponseEntity<List<Post>> getPersonalFeed(HttpServletRequest request) {
        String username = util.getUsernameFromRequest(request);
        List<Post> posts = new ArrayList<>();
        for(Follow follow : followRepository.findByFollower(username)){
            for(Post post : postRepository.findAll()){
                if(post.getUsername().equals(follow.getFollowing())){
                    posts.add(post);
                }
            }
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    public ResponseEntity<List<Post>> getCurrentUserPosts(HttpServletRequest request) {
        String username = util.getUsernameFromRequest(request);
        List<Post> posts = postRepository.findByUsername(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    public ResponseEntity<Post> getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }

    public ResponseEntity<String> updatePost(HttpServletRequest request, Long postId,Post post) {
        Post post_from_database = postRepository.findById(postId).orElse(null);
        if(post_from_database == null){
            return new ResponseEntity<>("Post unavailable for updation",HttpStatus.NOT_FOUND);
        }
        String username = util.getUsernameFromRequest(request);
       if(!username.equals(post_from_database.getUsername())){
           return new  ResponseEntity<>("Not your post!",HttpStatus.UNAUTHORIZED);
       }
       post_from_database.setContent(post.getContent());
       postRepository.save(post_from_database);
       return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
    }
    public ResponseEntity<String> deletePost(HttpServletRequest request, Long postId) {
        Post post_from_database = postRepository.findById(postId).orElse(null);
        if(post_from_database == null){
            return new ResponseEntity<>("Post unavailable for deletion",HttpStatus.NOT_FOUND);
        }
        String username = util.getUsernameFromRequest(request);
        if(!username.equals(post_from_database.getUsername())){
            return new  ResponseEntity<>("Not your post!",HttpStatus.UNAUTHORIZED);
        }
        postRepository.delete(post_from_database);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<List<Post>> getStarredPosts(HttpServletRequest request) {
        String currentUserName =  util.getUsernameFromRequest(request);
        List<Star> stars = starRepository.findByUsername(currentUserName);
        List<Post>posts = new ArrayList<>();
        for(Star star : stars){
            Long id = star.getPostId();
            posts.add(postRepository.findById(id).orElse(null));
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
