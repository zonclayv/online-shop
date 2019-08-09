package by.haidash.shop.product.entity;

import by.haidash.shop.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Category extends BaseEntity<Long> {

    private String name;

    @ManyToOne
    private Category parent;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!name.equals(category.name)) return false;
        return parent != null ? parent.equals(category.parent) : category.parent == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }
}
