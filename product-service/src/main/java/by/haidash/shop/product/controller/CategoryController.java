package by.haidash.shop.product.controller;

import by.haidash.shop.product.data.CategoryDto;
import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.exception.CategoryNotFoundException;
import by.haidash.shop.product.repository.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Api(description = "Set of endpoints for creating, retrieving, updating and deleting of categories.")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    @ApiOperation("Returns list of all categories.")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a specific category by their identifier. 404 if does not exist.")
    public Category getCategory(@ApiParam("Id of the category to be obtained. Cannot be empty.")
                                    @PathVariable Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PostMapping("/")
    @ApiOperation("Creates a new category.")
    public Category addCategory(@ApiParam("Category information for a new category to be created.")
                                    @RequestBody CategoryDto dto){

        Category category = new Category();
        category.setName(dto.getName());
        category.setParent(dto.getParent());

        return categoryRepository.save(category);
    }

    @GetMapping("/top")
    @ApiOperation("Returns list of all top level categories (Categories with empty parent category).")
    public List<Category> getTopLevelCategories() {
        return categoryRepository.findByParentIsNull();
    }

    @GetMapping("/{id}/sub")
    @ApiOperation("Returns list of first level children categories (Categories with parent category with given id).")
    public List<Category> getSubCategories(@ApiParam("Id of the parent category. Cannot be empty.")
                                               @PathVariable Long id) {
        return categoryRepository.findByParent_ParentId(id);
    }
}
