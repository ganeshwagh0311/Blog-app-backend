package com.ganesh.blog.services;

import com.ganesh.blog.entities.Post;
import com.ganesh.blog.payload.PostDto;
import com.ganesh.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    //Create

    PostDto createPost(PostDto postDto, Integer categoryID, Integer userID);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single post
    PostDto getPostById(Integer postId);

    //get all post by category
    List<PostDto> getPostByCategory(Integer categoryId);

    //get all post by user
    List<PostDto> getPostByUSer(Integer userID);

    //search posts
    List<PostDto> searchPosts(Integer keyword);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> getPostByUser(Integer userId);
}