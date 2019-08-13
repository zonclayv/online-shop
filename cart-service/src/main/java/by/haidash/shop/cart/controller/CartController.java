package by.haidash.shop.cart.controller;

import by.haidash.shop.cart.exception.PermissionDeniedException;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.cart.entity.Cart;
import by.haidash.shop.cart.entity.OrderProduct;
import by.haidash.shop.cart.exception.CartNotFoundException;
import by.haidash.shop.cart.repository.CartRepository;
import by.haidash.shop.security.service.SecurityContextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/carts")
@Api(description = "Set of endpoints for creating, retrieving and updating of cart.")
public class CartController {

    private final SecurityContextService securityContextService;
    private final CartRepository cartRepository;

    @Autowired
    public CartController(SecurityContextService securityContextService, CartRepository cartRepository) {
        this.securityContextService = securityContextService;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/{cartId}")
    @ApiOperation("Returns a specific cart by their identifier. 404 if does not exist.")
    public Cart getCartById(@ApiParam("Id of the cart to be obtained. Cannot be empty.")
                                @PathVariable Long cartId) {
        return getCart(cartId);
    }

    @PostMapping
    @ApiOperation("Creates new cart.")
    public Cart createCart(@ApiParam("Cart information for a new cart to be created.")
                            @RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @PutMapping("/{cartId}/products/{productId}/{quantity}")
    @ApiOperation("Adds a given product with provided quantity to the specific cart by their identifier.")
    public Cart addProduct(@ApiParam("Id of the cart. Cannot be empty.")
                                @PathVariable  Long cartId,
                           @ApiParam("Id of the product. Cannot be empty.")
                                @PathVariable  Long productId,
                           @ApiParam("Product quantity. Cannot be empty.")
                                @PathVariable  Integer quantity) {
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
    @ApiOperation("Updates quantity of product with given id in the specific cart.")
    public Cart updateProductQuantity(@ApiParam("Id of the cart. Cannot be empty.")
                                          @PathVariable  Long cartId,
                                      @ApiParam("Id of the product. Cannot be empty.")
                                          @PathVariable  Long productId,
                                      @ApiParam("Product quantity. Cannot be empty.")
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
    @ApiOperation("Removes product with given id from the specific cart.")
    public Cart removeProduct(@ApiParam("Id of the cart. Cannot be empty.")
                                  @PathVariable  Long cartId,
                              @ApiParam("Id of the product. Cannot be empty.")
                                  @PathVariable  Long productId) {

        Cart cart = getCart(cartId);
        cart.getProducts()
                .removeIf(orderProduct-> Objects.equals(orderProduct.getId(), productId));

        return cartRepository.save(cart);
    }

    private Cart getCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id '" + cartId + "' not fount"));

        UserPrincipal userPrincipal = securityContextService.getUserPrincipal();
        if (!Objects.equals(cart.getUser(), userPrincipal.getId())) {
            throw new PermissionDeniedException("You do not have permission for getting cart with id '" + cartId + "'");
        }

        return cart;
    }
}
