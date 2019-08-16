package by.haidash.shop.product.entity;

import by.haidash.shop.jpa.entity.CommonNamedEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product extends CommonNamedEntity {

    // TODO add possibility to load/upload images. s3 bucket can be one of the possible solutions.
    // private List<Images> images;
    @ManyToMany
    @JoinTable(name = "product_keywords")
    private  List<Keyword> keywords;

    @ManyToMany
    @JoinColumn(name = "product_categories")
    private List<Category> categories;

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
}
