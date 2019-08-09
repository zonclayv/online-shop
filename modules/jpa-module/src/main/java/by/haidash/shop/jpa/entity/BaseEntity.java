package by.haidash.shop.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
