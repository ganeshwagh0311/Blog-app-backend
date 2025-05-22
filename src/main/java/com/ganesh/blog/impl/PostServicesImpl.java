package com.ganesh.blog.impl;



import com.ganesh.blog.entities.Category;
import com.ganesh.blog.entities.Post;
import com.ganesh.blog.entities.User;
import com.ganesh.blog.exceptions.ResourceNotFoundException;
import com.ganesh.blog.payload.PostDto;
import com.ganesh.blog.payload.PostResponse;
import com.ganesh.blog.repo.CategoryRepo;
import com.ganesh.blog.repo.PostRepo;
import com.ganesh.blog.repo.UserRepo;
import com.ganesh.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServicesImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer categoryID, Integer userID) {
        User user = this.userRepo.findById(Long.valueOf(userID)).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userID));

        Category category = this.categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "categroy id", categoryID));


        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        // Fetch the post to be updated
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        // Update post details
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        // Update category if provided
        if (postDto.getCategory() != null && postDto.getCategory().getCategoryID() != null) {
            Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryID())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", postDto.getCategory().getCategoryID()));
            post.setCategory(category);
        }

        // Update image if provided
        if (postDto.getImageName() != null && !postDto.getImageName().isEmpty()) {
            post.setImageName(postDto.getImageName());
        }

        // Save the updated post
        Post updatedPost = this.postRepo.save(post);

        // Convert the updated post to DTO and return
        return this.modelMapper.map(updatedPost, PostDto.class);
    }


    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();


        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }
    @Transactional
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "posts id", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
        List<Post> posts = this.postRepo.findAllByCategory(cat);

        List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDto;
    }

    @Override
    public List<PostDto> getPostByUSer(Integer userID) {
        User user = this.userRepo.findById(Long.valueOf(userID)).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userID));
        List<Post> posts = this.postRepo.getAllByUser(user);
        List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public List<PostDto> searchPosts(Integer keyword) {
        Optional<Post> posts = this.postRepo.findById(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        return List.of();
    }
}