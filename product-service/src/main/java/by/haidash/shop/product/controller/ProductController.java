package by.haidash.shop.product.controller;

import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.entity.Product;
import by.haidash.shop.product.exception.CategoryNotFoundException;
import by.haidash.shop.product.exception.KeywordNotFoundException;
import by.haidash.shop.product.exception.ProductNotFoundException;
import by.haidash.shop.product.repository.CategoryRepository;
import by.haidash.shop.product.repository.KeywordRepository;
import by.haidash.shop.product.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Api(description = "Set of endpoints for creating, retrieving, updating and deleting of product.")
public class ProductController {

    private final ProductRepository productRepository;
    private final KeywordRepository keywordRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductRepository productRepository,
                             KeywordRepository keywordRepository,
                             CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.keywordRepository = keywordRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    @ApiOperation("Returns list of all products.")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a specific product by their identifier. 404 if does not exist.")
    public Product getProduct(@ApiParam("Id of the product to be obtained. Cannot be empty.")
                                  @PathVariable  Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/")
    @ApiOperation("Creates a new product.")
    public Product addProduct(@ApiParam("Product information for a new product to be created.")
                                  @RequestBody Product product){

         return productRepository.save(product);
    }

    @PutMapping("/{productId}/keywords/{keywordId}")
    @ApiOperation("Adds keyword for given product. 404 if keyword does not exist.")
    public Product addKeyword(@ApiParam("Id of the product. Cannot be empty.")
                                  @PathVariable Long productId,
                              @ApiParam("Id of the keyword. Cannot be empty.")
                                  @PathVariable Long keywordId){

        Product product = getProduct(productId);
        Keyword keyword = keywordRepository.findById(keywordId)
                .orElseThrow(() -> new KeywordNotFoundException(keywordId));

        List<Keyword> keywords = product.getKeywords();
        if(keywords.isEmpty() || !keywords.contains(keyword)){
            keywords.add(keyword);
            return productRepository.save(product);
        }

        return product;
    }

    @PutMapping("/{productId}/categories/{categoryId}")
    @ApiOperation("Adds category for given product. 404 if category does not exist.")
    public Product addCategory(@ApiParam("Id of the product. Cannot be empty.")
                                  @PathVariable Long productId,
                               @ApiParam("Id of the category. Cannot be empty.")
                                  @PathVariable Long categoryId){

        Product product = getProduct(productId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        List<Category> categories = product.getCategories();
        if(categories.isEmpty() || !categories.contains(category)){
            categories.add(category);
            return productRepository.save(product);
        }

        return product;
    }

    @GetMapping("/keywords/{keywordId}")
    @ApiOperation("Gets all products with given keyword.")
    public List<Product> findByKeyword(@ApiParam("Id of the keyword. Cannot be empty.")
                                            @PathVariable Long keywordId) {

        return productRepository.findByKeywords_id(keywordId);
    }

    @GetMapping("/categories/{categoryId}")
    @ApiOperation("Gets all products with given category.")
    public List<Product> findByCategory(@ApiParam("Id of the category. Cannot be empty.")
                                             @PathVariable Long categoryId) {

        return productRepository.findByCategories_id(categoryId);
    }
}
