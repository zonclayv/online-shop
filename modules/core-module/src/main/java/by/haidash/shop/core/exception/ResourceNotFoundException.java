package by.haidash.shop.core.exception;

public class ResourceNotFoundException extends BaseRuntimeException {

    public static final String RESOURCE_NOT_FOUND_EXCEPTION_CODE = "404-000";

    public ResourceNotFoundException(String msg, String code) {
        super(msg, code);
    }

    public ResourceNotFoundException(String msg) {
        super(msg, RESOURCE_NOT_FOUND_EXCEPTION_CODE);
    }
}
