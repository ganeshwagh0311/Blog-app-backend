package com.ganesh.blog.impl;

import com.ganesh.blog.entities.Category;
import com.ganesh.blog.exceptions.ResourceNotFoundException;
import com.ganesh.blog.payload.CategoryDto;
import com.ganesh.blog.repo.CategoryRepo;
import com.ganesh.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
    private CategoryRepo categoryRepo;
@Autowired
private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category cat= this.modelMapper.map(categoryDto,Category.class);
        Category addedCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryID) {
        System.out.println("UpdateCategory API Called for Category ID: " + categoryID);

        // Fetch existing category from the database
        Category category = categoryRepo.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryID));

        System.out.println("Existing Category Found: " + category);

        // ✅ Updating the fields only if they are different
        boolean isUpdated = false;

        if (!category.getCategoryTitle().equals(categoryDto.getCategoryTitle())) {
            category.setCategoryTitle(categoryDto.getCategoryTitle());
            isUpdated = true;
        }

        if (!category.getCategoryDescription().equals(categoryDto.getCategoryDescription())) {
            category.setCategoryDescription(categoryDto.getCategoryDescription());
            isUpdated = true;
        }

        if (isUpdated) {
            // Save the updated category in the database
            category = categoryRepo.save(category);
            System.out.println("Category Updated Successfully: " + category);
        } else {
            System.out.println("No Changes Detected, Skipping Update.");
        }

        // Convert entity to DTO and return updated object
        return convertToDto(category);
    }
    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryID(category.getCategoryID());
        dto.setCategoryTitle(category.getCategoryTitle());
        dto.setCategoryDescription(category.getCategoryDescription());
        return dto;
    }


    @Override
    public void deleteCategory(Integer categoryId)
    {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","category",categoryId));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories; // ✅ Return actual data
    }



    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();


        return categories.stream()
                .map(cat -> this.modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
    }




}
