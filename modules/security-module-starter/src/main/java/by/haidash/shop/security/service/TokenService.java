package by.haidash.shop.security.service;

import by.haidash.shop.security.model.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.List;

public interface TokenService {

    String createToken(String username, Long userId, List<String> authorities);

    UserPrincipal resolveToken(HttpServletRequest request);
}
