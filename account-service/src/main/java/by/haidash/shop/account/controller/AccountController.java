package by.haidash.shop.account.controller;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class AccountController {

    @GetMapping("/accounts")
    public List<Object> all() {
        throw new NotImplementedException();
    }
}
