package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.CategoryDTO;
import com.modsen.pizzap.models.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryDTO category);

    void updateCategory(Long categoryId, CategoryDTO category);

    void deleteCategory(Long categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long categoryId);
}
