package by.haidash.shop.security.service;

import java.security.Key;

public interface KeyManagerService {

    Key getPublicKey();

    Key getPrivateKey();

}
