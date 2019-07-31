package by.haidash.shop.product.controller;

import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.exception.KeywordNotFoundException;
import by.haidash.shop.product.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KeywordController {

    @Autowired
    private KeywordRepository keywordRepository;

    @GetMapping("/keywords")
    public List<Keyword> getKeywords() {
        return keywordRepository.findAll();
    }

    @GetMapping("/keywords/{id}")
    public Keyword getKeyword(@PathVariable Long id) {

        return keywordRepository.findById(id)
                .orElseThrow(() -> new KeywordNotFoundException(id));
    }

    @PostMapping("/keywords")
    public Keyword addKeyword(String keywordName){

        return keywordRepository.findByName(keywordName)
                .orElseGet(() -> {
                    Keyword keyword = new Keyword();
                    keyword.setName(keywordName);

                    return keywordRepository.save(keyword);
                });
    }

    @PutMapping("/keywords/{id}")
    public Keyword addProduct(@PathVariable Long id, String name){

        return keywordRepository.findByName(name).orElseGet(() -> {

            Keyword keyword = keywordRepository.findById(id)
                    .orElseThrow(() -> new KeywordNotFoundException(id));
            keyword.setName(name);

            return keywordRepository.save(keyword);
        });
    }
}
