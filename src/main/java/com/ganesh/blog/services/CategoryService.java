package com.ganesh.blog.services;

import com.ganesh.blog.entities.Category;
import com.ganesh.blog.payload.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    //Create
CategoryDto createCategory(CategoryDto categoryDto);

    //Update
 CategoryDto updateCategory(CategoryDto categoryDto , Integer CategoryId);
    //delete
void  deleteCategory(Integer categoryId);
    //get
 CategoryDto getCategory(Integer categoryId);
    //get All
List<Category> getCategories();

    List<CategoryDto> getAllCategories();
}
