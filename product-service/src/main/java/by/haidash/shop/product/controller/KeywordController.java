package by.haidash.shop.product.controller;

import by.haidash.shop.core.exception.ResourceAlreadyExistException;
import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.service.EntityMapperService;
import by.haidash.shop.product.controller.details.KeywordDetails;
import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private final KeywordRepository keywordRepository;
    private final EntityMapperService entityMapperService;

    @Autowired
    public KeywordController(KeywordRepository keywordRepository,
                             EntityMapperService entityMapperService) {
        this.keywordRepository = keywordRepository;
        this.entityMapperService = entityMapperService;
    }

    @GetMapping
    public List<KeywordDetails> getAllKeywords() {

        return keywordRepository.findAll().stream()
                .map(keyword -> entityMapperService.convertToDetails(keyword, KeywordDetails.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public KeywordDetails getKeyword(@PathVariable Long id) {

        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find keyword with id " + id));
        return entityMapperService.convertToDetails(keyword, KeywordDetails.class);
    }

    @PostMapping
    public KeywordDetails addKeyword(@RequestBody String keywordName){

        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElseGet(() -> {
                    Keyword newKeyword = new Keyword();
                    newKeyword.setName(keywordName);

                    return keywordRepository.save(newKeyword);
                });

        return entityMapperService.convertToDetails(keyword, KeywordDetails.class);
    }

    @PatchMapping("/{id}")
    public KeywordDetails renameKeyword(@PathVariable Long id,
                                        @RequestBody String name){

        keywordRepository.findByName(name).ifPresent(keyword -> {
            throw new ResourceAlreadyExistException("KeywordDetails with given name '"+ name +"' already exist");
        });

        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find keyword with id "+ id));
        keyword.setName(name);

        Keyword updatedKeyword = keywordRepository.save(keyword);
        return entityMapperService.convertToDetails(updatedKeyword, KeywordDetails.class);
    }
}
