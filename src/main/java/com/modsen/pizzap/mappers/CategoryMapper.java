package com.modsen.pizzap.mappers;

import org.springframework.stereotype.Service;
import com.modsen.pizzap.models.Category;
import com.modsen.pizzap.dto.CategoryDTO;
import java.util.function.Function;

@Service
public class CategoryMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getCategoryName()
        );
    }

    public Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        return new Category(
                categoryDTO.id(),
                categoryDTO.categoryName()
        );
    }
}