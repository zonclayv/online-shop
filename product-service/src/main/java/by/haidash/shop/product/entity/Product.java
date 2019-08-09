package by.haidash.shop.product.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;

    // TODO add possibility to load/upload images. s3 bucket can be one of the possible solutions.
    // private List<Images> images;
    @ManyToMany
    @JoinTable(name = "product_keywords")
    private  List<Keyword> keywords;

    @ManyToMany
    @JoinColumn(name = "product_categories")
    private List<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!name.equals(product.name)) return false;
        if (keywords != null ? !keywords.equals(product.keywords) : product.keywords != null) return false;
        return categories != null ? categories.equals(product.categories) : product.categories == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }
}
