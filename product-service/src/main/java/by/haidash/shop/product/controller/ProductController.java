package by.haidash.shop.product.controller;

import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.service.EntityMapperService;
import by.haidash.shop.product.controller.details.ProductDetails;
import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.entity.Product;
import by.haidash.shop.product.repository.CategoryRepository;
import by.haidash.shop.product.repository.KeywordRepository;
import by.haidash.shop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final KeywordRepository keywordRepository;
    private final CategoryRepository categoryRepository;
    private final EntityMapperService entityMapperService;

    @Autowired
    public ProductController(ProductRepository productRepository,
                             KeywordRepository keywordRepository,
                             CategoryRepository categoryRepository,
                             EntityMapperService entityMapperService) {
        this.productRepository = productRepository;
        this.keywordRepository = keywordRepository;
        this.categoryRepository = categoryRepository;
        this.entityMapperService = entityMapperService;
    }

    @GetMapping
    public List<ProductDetails> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> entityMapperService.convertToDetails(product, ProductDetails.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDetails getProduct(@PathVariable  Long id) {

        Product product = getProductEntity(id);
        return entityMapperService.convertToDetails(product, ProductDetails.class);
    }

    @PostMapping
    public ProductDetails addProduct(@RequestBody ProductDetails productDetails) {

        Product product = entityMapperService.convertToEntity(productDetails, Product.class);
        Product createdProduct = productRepository.save(product);
        return entityMapperService.convertToDetails(createdProduct, ProductDetails.class);
    }

    @PutMapping("/{productId}/keywords/{keywordId}")
    public ProductDetails addKeyword(@PathVariable Long productId,
                                     @PathVariable Long keywordId) {

        Product product = getProductEntity(productId);
        Keyword keyword = keywordRepository.findById(keywordId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find keyword with id "+ keywordId));

        List<Keyword> keywords = product.getKeywords();
        if (keywords.isEmpty() || !keywords.contains(keyword)) {
            keywords.add(keyword);
            product =  productRepository.save(product);
        }

        return entityMapperService.convertToDetails(product, ProductDetails.class);
    }

    @PutMapping("/{productId}/categories/{categoryId}")
    public ProductDetails addCategory(@PathVariable Long productId,
                               @PathVariable Long categoryId){

        Product product = getProductEntity(productId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category with id "+ categoryId));

        List<Category> categories = product.getCategories();
        if(categories.isEmpty() || !categories.contains(category)){
            categories.add(category);
            product =  productRepository.save(product);
        }

        return entityMapperService.convertToDetails(product, ProductDetails.class);
    }

    @GetMapping("/keywords/{keywordId}")
    public List<ProductDetails> findByKeyword(@PathVariable Long keywordId) {

        return productRepository.findByKeywords_id(keywordId).stream()
                .map(product -> entityMapperService.convertToDetails(product, ProductDetails.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{categoryId}")
    public List<ProductDetails> findByCategory(@PathVariable Long categoryId) {

        return productRepository.findByCategories_id(categoryId).stream()
                .map(product -> entityMapperService.convertToDetails(product, ProductDetails.class))
                .collect(Collectors.toList());
    }

    private Product getProductEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find product with id " + id));
    }
}
