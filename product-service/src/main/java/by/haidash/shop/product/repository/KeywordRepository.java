package by.haidash.shop.product.repository;

import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByName(String name);
}
