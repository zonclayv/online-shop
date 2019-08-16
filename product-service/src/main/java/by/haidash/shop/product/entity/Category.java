package by.haidash.shop.product.entity;

import by.haidash.shop.jpa.entity.CommonNamedEntity;

import javax.persistence.*;

@Entity
public class Category extends CommonNamedEntity {

    @ManyToOne
    private Category parent;

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
