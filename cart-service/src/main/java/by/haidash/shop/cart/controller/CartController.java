package by.haidash.shop.cart.controller;

import by.haidash.shop.cart.exception.PermissionDeniedException;
import by.haidash.shop.security.exception.WrongAuthenticationTokenException;
import by.haidash.shop.security.util.JwtUtil;
import by.haidash.shop.cart.entity.Cart;
import by.haidash.shop.cart.entity.OrderProduct;
import by.haidash.shop.cart.exception.CartNotFoundException;
import by.haidash.shop.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/carts/{cartId}")
    public Cart getCartById(HttpServletRequest request, @PathVariable Long cartId) {
        return getCart(request, cartId);
    }

    @PutMapping("/carts/{cartId}/products/{productId}/{quantity}")
    public Cart addProduct(HttpServletRequest request,
                           @PathVariable  Long cartId,
                           @PathVariable  Long productId,
                           @PathVariable  Integer quantity) {
        Cart cart = getCart(request, cartId);
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

    @PatchMapping("/carts/{cartId}/products/{productId}/{quantity}")
    public Cart updateProductQuantity(HttpServletRequest request,
                                      @PathVariable  Long cartId,
                                      @PathVariable  Long productId,
                                      @PathVariable  Integer quantity) {

        Cart cart = getCart(request, cartId);
        cart.getProducts()
                .stream()
                .filter(orderProduct -> Objects.equals(orderProduct.getId(), productId))
                .findFirst()
                .ifPresent(orderProduct-> orderProduct.setQuantity(quantity));

        return cartRepository.save(cart);
    }

    @DeleteMapping("/carts/{cartId}/products/{productId}")
    public Cart removeProduct(HttpServletRequest request,
                              @PathVariable  Long cartId,
                              @PathVariable  Long productId) {

        Cart cart = getCart(request, cartId);
        cart.getProducts()
                .removeIf(orderProduct-> Objects.equals(orderProduct.getId(), productId));

        return cartRepository.save(cart);
    }

    private Cart getCart(HttpServletRequest request, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id '" + cartId + "' not fount"));

        Long userId = jwtUtil.parseUserId(request)
                .orElseThrow(() -> new WrongAuthenticationTokenException("Wrong authentication token"));

        if (Objects.equals(cart.getUser(), userId)) {
            throw new PermissionDeniedException("You do not have permission for getting cart with id '" + cartId + "'");
        }

        return cart;
    }
}
