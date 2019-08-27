package by.haidash.shop.cart.controller;

import by.haidash.shop.cart.controller.details.CartDetails;
import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.service.EntityMapperService;
import by.haidash.shop.security.exception.PermissionDeniedException;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.cart.entity.Cart;
import by.haidash.shop.cart.entity.OrderProduct;
import by.haidash.shop.cart.repository.CartRepository;
import by.haidash.shop.security.service.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final ContextService contextService;
    private final CartRepository cartRepository;
    private final EntityMapperService entityMapperService;

    @Autowired
    public CartController(ContextService contextService,
                          CartRepository cartRepository,
                          EntityMapperService entityMapperService) {

        this.contextService = contextService;
        this.cartRepository = cartRepository;
        this.entityMapperService = entityMapperService;
    }

    @GetMapping("/{cartId}")
    public CartDetails getCartById(@PathVariable Long cartId) {

        Cart cart = getCart(cartId);
        return entityMapperService.convertToDetails(cart, CartDetails.class);
    }

    @PostMapping
    public CartDetails createCart(@RequestBody CartDetails cartDetails) {

        Cart cart = entityMapperService.convertToEntity(cartDetails, Cart.class);
        Cart createdCart = cartRepository.save(cart);
        return entityMapperService.convertToDetails(createdCart, CartDetails.class);
    }

    @PutMapping("/{cartId}/products/{productId}/{quantity}")
    public CartDetails addProductWithQuantity(@PathVariable Long cartId,
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

        Cart updatedCart = cartRepository.save(cart);
        return entityMapperService.convertToDetails(updatedCart, CartDetails.class);
    }

    @PatchMapping("/{cartId}/products/{productId}/{quantity}")
    public CartDetails updateProductQuantity(@PathVariable  Long cartId,
                                             @PathVariable  Long productId,
                                             @PathVariable  Integer quantity) {

        Cart cart = getCart(cartId);
        cart.getProducts()
                .stream()
                .filter(orderProduct -> Objects.equals(orderProduct.getId(), productId))
                .findFirst()
                .ifPresent(orderProduct-> orderProduct.setQuantity(quantity));

        Cart updatedCart = cartRepository.save(cart);
        return entityMapperService.convertToDetails(updatedCart, CartDetails.class);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public CartDetails removeProduct(@PathVariable  Long cartId,
                                     @PathVariable  Long productId) {

        Cart cart = getCart(cartId);
        cart.getProducts()
                .removeIf(orderProduct-> Objects.equals(orderProduct.getId(), productId));

        Cart updatedCart = cartRepository.save(cart);
        return entityMapperService.convertToDetails(updatedCart, CartDetails.class);
    }

    private Cart getCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id '" + cartId + "' not found"));

        UserPrincipal userPrincipal = contextService.getUserPrincipal();
        if (!Objects.equals(cart.getUser(), userPrincipal.getId())) {
            throw new PermissionDeniedException("You do not have permission for getting cart with id '" + cartId + "'");
        }

        return cart;
    }
}
