package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.CategoryDTO;
import com.modsen.pizzap.models.Category;
import com.modsen.pizzap.services.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Data
@RestController
@RequestMapping("v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody CategoryDTO category) {
        categoryService.createCategory(category);
    }

    @PostMapping("/updateCategory/{categoryId}")
    public void updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO category) {
        categoryService.updateCategory(categoryId, category);
    }

    @GetMapping("/getCategory/{categoryId}")
    public CategoryDTO getCategoryById(@PathVariable Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/getAllCategories")
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
    }
}
