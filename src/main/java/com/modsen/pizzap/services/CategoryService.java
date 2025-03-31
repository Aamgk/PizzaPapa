package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO category);

    void deleteCategory(Long categoryId);

    Page<CategoryDTO> getAllCategories(Pageable pageable);

    CategoryDTO getCategoryById(Long categoryId);
}
