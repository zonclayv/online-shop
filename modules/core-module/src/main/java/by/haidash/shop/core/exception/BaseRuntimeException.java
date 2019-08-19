package by.haidash.shop.core.exception;

import java.util.concurrent.ThreadLocalRandom;

public class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(String msg, String code){
        super("Code " + code + ": "+ msg);
    }

    public BaseRuntimeException(String msg){
        this(msg, "000-" + String.valueOf(ThreadLocalRandom.current().nextInt()));
    }
}
