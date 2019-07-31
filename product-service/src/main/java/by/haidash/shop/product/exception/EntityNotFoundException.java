package by.haidash.shop.product.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("Could not find %s with id %s", entityName, id));
    }
}
