package by.haidash.shop.product.controller;

import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.exception.KeywordAlreadyExistException;
import by.haidash.shop.product.exception.KeywordNotFoundException;
import by.haidash.shop.product.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordController(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @GetMapping
    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    @GetMapping("/{id}")
    public Keyword getKeyword(@PathVariable Long id) {

        return keywordRepository.findById(id)
                .orElseThrow(() -> new KeywordNotFoundException(id));
    }

    @PostMapping
    public Keyword addKeyword(@RequestBody String keywordName){

        return keywordRepository.findByName(keywordName)
                .orElseGet(() -> {
                    Keyword keyword = new Keyword();
                    keyword.setName(keywordName);

                    return keywordRepository.save(keyword);
                });
    }

    @PatchMapping("/{id}")
    public Keyword renameKeyword(@PathVariable Long id,
                                 @RequestBody String name){

        keywordRepository.findByName(name)
                .orElseThrow(() -> new KeywordAlreadyExistException("Keyword with given name '"+ name +"' already exist"));

        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new KeywordNotFoundException(id));
        keyword.setName(name);

        return keywordRepository.save(keyword);
    }
}
