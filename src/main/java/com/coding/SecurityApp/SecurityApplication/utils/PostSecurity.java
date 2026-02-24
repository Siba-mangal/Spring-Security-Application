package com.coding.SecurityApp.SecurityApplication.utils;

import com.coding.SecurityApp.SecurityApplication.dto.PostDTO;
import com.coding.SecurityApp.SecurityApplication.entities.User;
import com.coding.SecurityApp.SecurityApplication.services.PostService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
