package by.haidash.shop.cart.controller;

import by.haidash.shop.security.exception.WrongAuthenticationTokenException;
import by.haidash.shop.security.util.JwtUtil;
import by.haidash.shop.cart.entity.Cart;
import by.haidash.shop.cart.entity.CartStatus;
import by.haidash.shop.cart.entity.OrderProduct;
import by.haidash.shop.cart.exception.CartNotFoundException;
import by.haidash.shop.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/cart")
    public Cart getCart(HttpServletRequest request) {

        Long userId = jwtUtil.parseUserId(request)
                .orElseThrow(() -> new WrongAuthenticationTokenException("Wrong authentication token"));

        return cartRepository.findByUserAndStatuses(userId, Arrays.asList(CartStatus.NEW, CartStatus.CHECKOUT))
                .orElseGet(() -> crateNewCart(userId));
    }

    @PutMapping("/cart/product/{productId}/{quantity}")
    public Cart addProduct(HttpServletRequest request,
                           @PathVariable  Long productId,
                           @PathVariable  Integer quantity) {

        Long userId = jwtUtil.parseUserId(request)
                .orElseThrow(() -> new WrongAuthenticationTokenException("Wrong authentication token"));

        Cart cart = cartRepository.findByUserAndStatuses(userId, Arrays.asList(CartStatus.NEW, CartStatus.CHECKOUT))
                .orElseGet(() -> crateNewCart(userId));

        List<OrderProduct> products = cart.getProducts();
        OrderProduct product = products.stream()
                .filter(orderProduct -> Objects.equals(orderProduct.getId(), productId))
                .findFirst()
                .orElseGet(() -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProduct(productId);
                    orderProduct.setQuantity(0);

                    products.add(orderProduct);

                    return orderProduct;
                });

        product.setQuantity(product.getQuantity() + quantity);

        return cartRepository.save(cart);
    }

    @PatchMapping("/cart/product/{productId}/{quantity}")
    public Cart updateProductQuantity(HttpServletRequest request,
                           @PathVariable  Long productId,
                           @PathVariable  Integer quantity) {

        Long userId = jwtUtil.parseUserId(request)
                .orElseThrow(() -> new WrongAuthenticationTokenException("Wrong authentication token"));

        Cart cart = cartRepository.findByUserAndStatuses(userId, Arrays.asList(CartStatus.NEW, CartStatus.CHECKOUT))
                .orElseGet(() -> crateNewCart(userId));

        List<OrderProduct> products = cart.getProducts();
        products.stream()
                .filter(orderProduct -> Objects.equals(orderProduct.getId(), productId))
                .findFirst()
                .ifPresent(orderProduct-> orderProduct.setQuantity(quantity));

        return cartRepository.save(cart);
    }

    @DeleteMapping("/cart/product/{productId}")
    public Cart removeProduct(HttpServletRequest request, @PathVariable  Long productId) {

        Long userId = jwtUtil.parseUserId(request)
                .orElseThrow(() -> new WrongAuthenticationTokenException("Wrong authentication token"));

        Cart cart = cartRepository.findByUserAndStatuses(userId, Arrays.asList(CartStatus.NEW, CartStatus.CHECKOUT))
                .orElseThrow(() -> new CartNotFoundException("Cart not fount for current user"));

        List<OrderProduct> products = cart.getProducts();
        products.removeIf(orderProduct-> Objects.equals(orderProduct.getId(), productId));

        return cartRepository.save(cart);
    }

    private Cart crateNewCart(Long userId) {

        Cart newCart = new Cart();
        newCart.setStatus(CartStatus.NEW);
        newCart.setCreationDate(LocalDateTime.now());
        newCart.setUser(userId);

        return newCart;
    }
}
