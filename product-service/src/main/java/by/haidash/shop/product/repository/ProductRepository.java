package by.haidash.shop.product.repository;

import by.haidash.shop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_CategoryId(Long categoryId);
    List<Product> findByKeyword_KeywordId(Long keywordId);
}
