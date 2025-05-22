package com.ganesh.blog.services;

import com.ganesh.blog.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);


}
