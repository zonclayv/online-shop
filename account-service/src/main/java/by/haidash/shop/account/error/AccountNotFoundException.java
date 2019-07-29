package by.haidash.shop.account.error;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("Could not find account " + id);
    }
}