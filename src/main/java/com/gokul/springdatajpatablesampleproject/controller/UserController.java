package com.gokul.springdatajpatablesampleproject.controller;
import com.gokul.springdatajpatablesampleproject.model.*;
import com.gokul.springdatajpatablesampleproject.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("user")

public class UserController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    Util util;

    @Autowired
    FollowService followService;

    @Autowired
    StarService starService;

    @Autowired
    CommentService commentService;

    @PostMapping("addRole")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @GetMapping("{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username){
        return new ResponseEntity<>(util.getUserDTOByUsername(username), HttpStatus.OK);
    }

    @GetMapping("me")
    public ResponseEntity<UserDTO> getMe(HttpServletRequest request){
        return new ResponseEntity<>(util.getCurrentUserDTO(request), HttpStatus.OK);
    }

    @PutMapping("profile")
    public ResponseEntity<String> updateProfile(HttpServletRequest request, @RequestBody UserDTO userDTO){
        return userService.updateCurrentUser(request, userDTO);
    }

    @PostMapping("post")
    public ResponseEntity<String> post(HttpServletRequest request, @RequestBody Post post){
        return postService.addPost(request,post);
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<String> follow(@PathVariable String username,HttpServletRequest request){
        return followService.addFollower(username,request);
    }

    @DeleteMapping("follow/{username}")
    public ResponseEntity<String> unfollow(@PathVariable String username,HttpServletRequest request){
        return followService.deleteFollower(username,request);
    }

    @GetMapping("follow/{username}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable String username,HttpServletRequest request){
        return followService.isFollowing(request,username);
    }

    @GetMapping("followers")
    public ResponseEntity<List<UserDTO>> getFollowers(HttpServletRequest request){
        return followService.getFollowers(request);
    }

    @GetMapping("following")
    public ResponseEntity<List<UserDTO>> getFollowing(HttpServletRequest request){
        return followService.getFollowing(request);
    }


    @GetMapping("personalFeed")
    public ResponseEntity<List<Post>> getPersonalFeed(HttpServletRequest request){
        return postService.getPersonalFeed(request);
    }

    @GetMapping("myposts")
    public ResponseEntity<List<Post>> getCurrentUserPosts(HttpServletRequest request){
        return postService.getCurrentUserPosts(request);
    }

    @GetMapping("post/{post_id}")
    public ResponseEntity<Post> getPost(@PathVariable("post_id") Long post_id){
        return postService.getPostById(post_id);
    }

    @PutMapping("post/update/{post_id}")
    public ResponseEntity<String> updatePost(HttpServletRequest request, @PathVariable Long post_id, @RequestBody Post post){
        return postService.updatePost(request,post_id,post);
    }

    @DeleteMapping("post/delete/{post_id}")
    public ResponseEntity<String> deletePost(HttpServletRequest request, @PathVariable Long post_id){
        return postService.deletePost(request,post_id);
    }

    @PostMapping("star/{post_id}")
    public ResponseEntity<String> star(HttpServletRequest request,@PathVariable Long post_id){
        return starService.addStar(request,post_id);
    }

    @DeleteMapping("unstar/{post_id}")
    public ResponseEntity<String> unstar(HttpServletRequest request,@PathVariable Long post_id){
        return starService.unStar(request,post_id);
    }

    @GetMapping("starred")
    public ResponseEntity<List<Post>> getStarredPosts(HttpServletRequest request){
        return postService.getStarredPosts(request);
    }
    @PostMapping("comment/{post_id}")
    public ResponseEntity<String> addComment(HttpServletRequest request, @PathVariable Long post_id, @RequestBody Comment comment){
        return commentService.addComment(request,post_id,comment);
    }
    @GetMapping("comment/{post_id}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long post_id){
        return commentService.getComment(post_id);
    }
    @PutMapping("comment/update/{comment_id}")
    public ResponseEntity<String> updateComment(HttpServletRequest request, @PathVariable Long comment_id, @RequestBody Comment comment){
        return commentService.updateComment(request,comment_id,comment);
    }
    @DeleteMapping("/comment/delete/{comment_id}")
    public ResponseEntity<String> deleteComment(HttpServletRequest request, @PathVariable Long comment_id){
        return commentService.deleteComment(request,comment_id);
    }
}