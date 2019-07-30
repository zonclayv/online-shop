package by.haidash.shop.product.repository;

import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNull();
    List<Category> findByParent_ParentId(Long parentId);
}
