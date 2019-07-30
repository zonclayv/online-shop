package by.haidash.shop.product.controller;

import by.haidash.shop.product.data.CategoryDto;
import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.error.CategoryNotFoundException;
import by.haidash.shop.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category getCategories(@PathVariable Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PostMapping("/categories")
    public Category addCategory(CategoryDto dto){

        Category category = new Category();
        category.setName(dto.getName());
        category.setParent(dto.getParent());

        return categoryRepository.save(category);
    }

    @GetMapping("/categories/top")
    public List<Category> getTopLavelCategories() {
        return categoryRepository.findByParentIsNull();
    }

    @GetMapping("/categories/{id}/sub")
    public List<Category> getSubCategories(@PathVariable Long id) {
        return categoryRepository.findByParent_ParentId(id);
    }
}
