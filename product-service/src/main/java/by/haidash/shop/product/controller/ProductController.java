package by.haidash.shop.product.controller;

import by.haidash.shop.product.entity.Product;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        throw new NotImplementedException();
    }
}
