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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO use model instead of sending entities in all controllers.

@RestController
@RequestMapping("/products")
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

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable  Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){

         return productRepository.save(product);
    }

    @PutMapping("/{productId}/keywords/{keywordId}")
    public Product addKeyword(@PathVariable Long productId,
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
    public Product addCategory(@PathVariable Long productId,
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
    public List<Product> findByKeyword(@PathVariable Long keywordId) {

        return productRepository.findByKeywords_id(keywordId);
    }

    @GetMapping("/categories/{categoryId}")
    public List<Product> findByCategory(@PathVariable Long categoryId) {

        return productRepository.findByCategories_id(categoryId);
    }
}
