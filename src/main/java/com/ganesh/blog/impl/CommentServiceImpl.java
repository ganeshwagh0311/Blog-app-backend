package com.ganesh.blog.impl;

import com.ganesh.blog.entities.Post;
import com.ganesh.blog.exceptions.ResourceNotFoundException;
import com.ganesh.blog.payload.CommentDto;
import com.ganesh.blog.repo.CommentRepo;
import com.ganesh.blog.repo.PostRepo;
import com.ganesh.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ganesh.blog.entities.Comment;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        // Fetching the Post object associated with postId
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        // Mapping CommentDto to Comment entity
        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        // Setting the Post for the Comment
        comment.setPost(post);

        // Saving the Comment entity
        Comment savedComment = this.commentRepo.save(comment);

        // Mapping the saved Comment entity to CommentDto and returning it
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        // Fetching the Comment entity associated with commentId
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));

        // Deleting the Comment entity
        this.commentRepo.delete(comment);
    }
}
