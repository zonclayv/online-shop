package by.haidash.shop.account.controller;

import by.haidash.shop.account.data.AccountDto;
import by.haidash.shop.account.entity.Account;
import by.haidash.shop.account.error.AccountNotFoundException;
import by.haidash.shop.account.error.EmailExistException;
import by.haidash.shop.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public List<Account> all() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account getById(@PathVariable Long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @PostMapping("/accounts")
    public Account register(AccountDto dto){

        // TODO need implement checking
        if (false) {
            throw new EmailExistException("There is an account with that email address:" + dto.getEmail());
        }

        // TODO add possibility to registration using just one field - email. After registration link for changing password should be sent.
        Account account = new Account();
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPsw(passwordEncoder.encode(dto.getPsw()));
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());

        return accountRepository.save(account);
    }
}
