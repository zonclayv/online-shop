package by.haidash.shop.product.controller.details;

import by.haidash.shop.core.controller.details.BaseDetails;

import javax.validation.constraints.NotEmpty;

public class CategoryDetails implements BaseDetails {

    private Long id;

    @NotEmpty
    private String name;

    private CategoryDetails parent;

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

    public CategoryDetails getParent() {
        return parent;
    }

    public void setParent(CategoryDetails parent) {
        this.parent = parent;
    }
}
