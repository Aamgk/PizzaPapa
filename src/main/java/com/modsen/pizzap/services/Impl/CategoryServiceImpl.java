package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.CategoryDTO;
import com.modsen.pizzap.mappers.CategoryMapper;
import com.modsen.pizzap.models.Category;
import com.modsen.pizzap.repositories.CategoryRepository;
import com.modsen.pizzap.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryDTO categoryDto) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long categoryId, CategoryDTO category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        existingCategory.setCategoryName(category.categoryName());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return new ArrayList<>(categoryRepository.findAll())
                .stream()
                .map(categoryMapper).toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
