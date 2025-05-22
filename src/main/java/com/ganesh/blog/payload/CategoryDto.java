package com.ganesh.blog.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryID;
    @NotBlank
    @Size(min=4,message="Min size of category title is 4")
    private String categoryTitle;
    @NotBlank
    @Size(min=10 ,message = "min size of category desc is 10  ")
    private String categoryDescription;


    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryName) {
        this.categoryTitle = categoryName;
    }



    @Override
    public String toString() {
        return "CategoryDto{" +
                "categoryID=" + categoryID +
                ", categoryName='" + categoryTitle + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }
}
