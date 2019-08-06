package by.haidash.shop.product.data;

import by.haidash.shop.product.entity.Category;
import by.haidash.shop.product.entity.Keyword;

import java.util.List;

public class ProductDto {

    private String name;

    private List<Keyword> keywords;

    private List<Category> categories;

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
}
