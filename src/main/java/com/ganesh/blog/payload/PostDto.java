package com.ganesh.blog.payload;

import com.ganesh.blog.entities.Category;
import com.ganesh.blog.entities.Comment;
import com.ganesh.blog.entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private LocalDateTime addedDate;

    private CategoryDto category;

    private UserDto user;

    public Integer getPostid() {
        return postId;
    }

    public void setPostid(Integer postid) {
        this.postId = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }

    private Set<CommentDto> comments = new HashSet<>();

    @Override
    public String toString() {
        return "PostDto{" +
                "postid=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageName='" + imageName + '\'' +
                ", addedDate=" + addedDate +
                ", category=" + category +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }


}
