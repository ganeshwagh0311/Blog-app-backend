package com.ganesh.blog.repo;

import com.ganesh.blog.entities.Post;
import com.ganesh.blog.entities.User;
import com.ganesh.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo  extends JpaRepository<Post,Integer> {

    List<Post> getAllByUser(User user);

    List<Post> findAllByCategory(Category category);

    @Query("SELECT p FROM Post p WHERE p.id LIKE CONCAT('%', :postid, '%')")
    List<Post> findByTitleContaining(@Param("postid") Integer postid);

}