package com.project.postinger_post_service.controller;

import com.project.postinger_post_service.jwt.JwtUtil;
import com.project.postinger_post_service.model.Post;
import com.project.postinger_post_service.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {


    private final PostService postService;
    private final JwtUtil jwtUtil;



    @PostMapping("/create-post")
    public ResponseEntity<Post> createPost(@RequestBody Post post, HttpServletRequest request){
        String jwt = postService.getToken(request);
        Long userId = jwtUtil.extractId(jwt);
        if(post.getId() == null && post.getUserId() == null){
            post.setUserId(userId);
            Post savedPost = postService.createOrupdatePost(post);
            return ResponseEntity.ok(savedPost);
        }
        else throw new RuntimeException("ID and UserID must be null");

    }

    @PutMapping("/update-post")
    public ResponseEntity<Post> updatePost(@RequestBody Post post,HttpServletRequest request){
        String jwt = postService.getToken(request);
        Long userId = jwtUtil.extractId(jwt);
        if (post.getId() != null && post.getUserId() == null) {
            post.setUserId(userId);
            Post updatePost = postService.createOrupdatePost(post);
            return ResponseEntity.ok(updatePost);
        }
         throw new RuntimeException(("ID must not null and USERID must be null"));


    }
    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId,HttpServletRequest request){
        String jwt = postService.getToken(request);
        Long userId = jwtUtil.extractId(jwt);
        System.out.println(postService.getPostById(postId));
        if (postId != null){
            if (postService.getPostById(postId).getUserId().equals(userId)){
                postService.deletePost(postId);
                return ResponseEntity.ok("Post deleted!");
            }else throw new RuntimeException("You can delete only your own posts");
        }else throw new RuntimeException("Post id must not be null");


    }


    @GetMapping("/find-all")
    public ResponseEntity<List<Post>> findAllPosts(HttpServletRequest request){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/find/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable String postId){

        return ResponseEntity.ok(postService.getPostById(postId));
    }


    @GetMapping("/user-posts/{userId}")
    public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable Long userId){

        return ResponseEntity.ok(postService.getPostByUserId(userId));
    }


}
