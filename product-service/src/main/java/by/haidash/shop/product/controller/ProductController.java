package by.haidash.shop.product.controller;

import by.haidash.shop.product.data.ProductDto;
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

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable  Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/products")
    public Product addProduct(ProductDto dto){

        Product product = new Product();
        product.setName(dto.getName());
        product.setKeywords(dto.getKeywords());
        product.setCategories(dto.getCategories());

        return productRepository.save(product);
    }

    @PutMapping("/products/{productId}/keywords/{keywordId}")
    public Product addKeyword(@PathVariable Long productId, @PathVariable Long keywordId){

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

    @PutMapping("/products/{productId}/category/{categoryId}")
    public Product addCategory(@PathVariable Long productId, @PathVariable Long categoryId){

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

    @GetMapping("/products/keyword/{keywordId}")
    public List<Product> findByKeyword (@PathVariable Long keywordId) {

        return productRepository.findByKeywords_id(keywordId);
    }

    @GetMapping("/products/category/{categoryId}")
    public List<Product> findByCategory (@PathVariable Long categoryId) {

        return productRepository.findByCategories_id(categoryId);
    }
}
