package by.haidash.shop.product.controller.details;

import by.haidash.shop.core.controller.details.BaseDetails;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ProductDetails implements BaseDetails {

    private Long id;

    @NotEmpty
    private String name;

    private  List<KeywordDetails> keywords;

    private List<CategoryDetails> categories;

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

    public List<KeywordDetails> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeywordDetails> keywords) {
        this.keywords = keywords;
    }

    public List<CategoryDetails> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDetails> categories) {
        this.categories = categories;
    }
}
