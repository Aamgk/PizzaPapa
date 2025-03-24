package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.CategoryDTO;
import com.modsen.pizzap.exception.DuplicateResourceException;
import com.modsen.pizzap.exception.ResourceNotFoundException;
import com.modsen.pizzap.exception.error.ErrorMessages;
import com.modsen.pizzap.mappers.CategoryMapper;
import com.modsen.pizzap.models.Category;
import com.modsen.pizzap.repositories.CategoryRepository;
import com.modsen.pizzap.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDto) {
        checkCategoryExistence(categoryDto.categoryName());

        Category category = categoryMapper.categoryDTOToCategory(categoryDto);
        categoryRepository.save(category);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<CategoryDTO> updateCategory(Long categoryId, CategoryDTO category) {
        Category existingCategory = findCategoryByIdOrThrow(categoryId);
        existingCategory.setCategoryName(category.categoryName());
        categoryRepository.save(existingCategory);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category existingCategory = findCategoryByIdOrThrow(categoryId);
        categoryRepository.deleteById(existingCategory.getId());
    }

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = findCategoryByIdOrThrow(categoryId);
        return categoryMapper.apply(category);
    }

    private Category findCategoryByIdOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "Category", categoryId)));
    }

    private void checkCategoryExistence (String categoryName){
        if(categoryRepository.existsByCategoryName(categoryName)){
            throw new DuplicateResourceException(String.format(ErrorMessages.DUPLICATE_RESOURCE_MESSAGE, "Category", "name"));
        }
    }
}
