package by.haidash.shop.cart.controller;

import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.security.exception.PermissionDeniedException;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.cart.entity.Cart;
import by.haidash.shop.cart.entity.OrderProduct;
import by.haidash.shop.cart.repository.CartRepository;
import by.haidash.shop.security.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final SecurityContextService securityContextService;
    private final CartRepository cartRepository;

    @Autowired
    public CartController(SecurityContextService securityContextService, CartRepository cartRepository) {
        this.securityContextService = securityContextService;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Long cartId) {
        return getCart(cartId);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @PutMapping("/{cartId}/products/{productId}/{quantity}")
    public Cart addProductWithQuantity(@PathVariable  Long cartId,
                                       @PathVariable Long productId,
                                       @PathVariable Integer quantity) {
        Cart cart = getCart(cartId);
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

    @PatchMapping("/{cartId}/products/{productId}/{quantity}")
    public Cart updateProductQuantity(@PathVariable  Long cartId,
                                      @PathVariable  Long productId,
                                      @PathVariable  Integer quantity) {

        Cart cart = getCart(cartId);
        cart.getProducts()
                .stream()
                .filter(orderProduct -> Objects.equals(orderProduct.getId(), productId))
                .findFirst()
                .ifPresent(orderProduct-> orderProduct.setQuantity(quantity));

        return cartRepository.save(cart);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public Cart removeProduct(@PathVariable  Long cartId,
                              @PathVariable  Long productId) {

        Cart cart = getCart(cartId);
        cart.getProducts()
                .removeIf(orderProduct-> Objects.equals(orderProduct.getId(), productId));

        return cartRepository.save(cart);
    }

    private Cart getCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id '" + cartId + "' not fount"));

        UserPrincipal userPrincipal = securityContextService.getUserPrincipal();
        if (!Objects.equals(cart.getUser(), userPrincipal.getId())) {
            throw new PermissionDeniedException("You do not have permission for getting cart with id '" + cartId + "'");
        }

        return cart;
    }
}
