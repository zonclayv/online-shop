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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        if (!super.equals(o)) return false;

        Category category = (Category) o;

        return parent != null ? parent.equals(category.parent) : category.parent == null;
    }
}
