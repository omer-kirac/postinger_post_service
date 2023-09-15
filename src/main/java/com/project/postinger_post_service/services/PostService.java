package com.project.postinger_post_service.services;



import com.project.postinger_post_service.model.Post;
import com.project.postinger_post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    public String getToken(HttpServletRequest request){
        log.info("[getToken()] started, {}", request.getAuthType());
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                System.out.println(cookie);
                if(cookie.getName().equals("jwtToken")){
                    jwt = cookie.getValue();
                    System.out.println(jwt);
                    break;
                }
            }
        }return jwt;
    }
    public Post createOrupdatePost(Post post){

        if (post.getId() == null){

            post.setId(UUID.randomUUID().toString());


        }
        return postRepository.save(post);
    }
    public void deletePost(String postId){
        postRepository.deleteById(postId);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }


    public Post getPostById(String postId){
        return postRepository.findById(postId).orElse(null);
    }


    public List<Post> getPostByUserId(Long userId){return postRepository.findAllByUserId(userId);
    }


}
