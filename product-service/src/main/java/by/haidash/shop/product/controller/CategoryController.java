package by.haidash.shop.product.controller;

import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.service.EntityMapperService;
import by.haidash.shop.product.controller.details.CategoryDetails;
import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final EntityMapperService entityMapperService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository,
                              EntityMapperService entityMapperService) {

        this.categoryRepository = categoryRepository;
        this.entityMapperService = entityMapperService;
    }

    @GetMapping
    public List<CategoryDetails> getAllCategories() {

        return categoryRepository.findAll().stream()
                .map(category -> entityMapperService.convertToDetails(category, CategoryDetails.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDetails getCategory(@PathVariable Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category with id " + id));
        return entityMapperService.convertToDetails(category, CategoryDetails.class);
    }

    @PostMapping
    public CategoryDetails addCategory(@RequestBody CategoryDetails categoryDetails) {

        Category category = entityMapperService.convertToEntity(categoryDetails, Category.class);
        Category createdCategory = categoryRepository.save(category);
        return entityMapperService.convertToDetails(createdCategory, CategoryDetails.class);
    }

    @GetMapping("/top")
    public List<CategoryDetails> getTopLevelCategories() {

        return categoryRepository.findByParentIsNull().stream()
                .map(category -> entityMapperService.convertToDetails(category, CategoryDetails.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/sub")
    public List<CategoryDetails> getSubCategories(@PathVariable Long id) {

        return categoryRepository.findByParent_ParentId(id).stream()
                .map(category -> entityMapperService.convertToDetails(category, CategoryDetails.class))
                .collect(Collectors.toList());
    }
}
