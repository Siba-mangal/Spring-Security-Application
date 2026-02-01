package com.coding.SecurityApp.SecurityApplication.services;


import com.coding.SecurityApp.SecurityApplication.dto.PostDTO;
import com.coding.SecurityApp.SecurityApplication.entities.PostEntity;
import com.coding.SecurityApp.SecurityApplication.entities.User;
import com.coding.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.coding.SecurityApp.SecurityApplication.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        PostEntity savedPost = postRepository.save(postEntity);
        
        PostDTO response = modelMapper.map(savedPost, PostDTO.class);
        response.setUserId(savedPost.getUser().getId());
        response.setUserName(savedPost.getUser().getName());
        return response;
    }

    @Override
    public PostDTO getPostById(Long postId) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user {}", user);

        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
        PostEntity olderPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost);
        PostEntity savedPostEntity = postRepository.save(olderPost);
        return modelMapper.map(savedPostEntity, PostDTO.class);
    }
}
