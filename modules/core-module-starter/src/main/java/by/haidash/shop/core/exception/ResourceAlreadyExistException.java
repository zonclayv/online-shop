package by.haidash.shop.core.exception;

public class ResourceAlreadyExistException extends BaseRuntimeException {

    public static final String RESOURCE_ALREADY_EXIST_EXCEPTION_CODE = "409-001";

    public ResourceAlreadyExistException(String msg, Exception cause) {
        super(msg, cause);
    }

    public ResourceAlreadyExistException(String msg) {
        super(msg, RESOURCE_ALREADY_EXIST_EXCEPTION_CODE);
    }
}
