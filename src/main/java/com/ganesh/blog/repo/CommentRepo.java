package com.ganesh.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ganesh.blog.entities.Comment; // ✅ Correct import

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
