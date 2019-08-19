package by.haidash.shop.product.controller;

import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.exception.CategoryNotFoundException;
import by.haidash.shop.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    @GetMapping("/top")
    public List<Category> getTopLevelCategories() {
        return categoryRepository.findByParentIsNull();
    }

    @GetMapping("/{id}/sub")
    public List<Category> getSubCategories(@PathVariable Long id) {
        return categoryRepository.findByParent_ParentId(id);
    }
}
