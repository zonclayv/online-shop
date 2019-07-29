package by.haidash.shop.catalog.controller;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogController {

    @GetMapping("/catalog")
    public List<Object> getCart() {
        throw new NotImplementedException();
    }
}
