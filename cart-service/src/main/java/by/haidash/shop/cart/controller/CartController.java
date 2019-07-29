package by.haidash.shop.cart.controller;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class CartController {

    @GetMapping("/cart")
    public Object getCart() {
        throw new NotImplementedException();
    }
}
