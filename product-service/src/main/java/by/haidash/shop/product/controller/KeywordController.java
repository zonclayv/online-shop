package by.haidash.shop.product.controller;

import by.haidash.shop.product.entity.Keyword;
import by.haidash.shop.product.exception.KeywordAlreadyExistException;
import by.haidash.shop.product.exception.KeywordNotFoundException;
import by.haidash.shop.product.repository.KeywordRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keywords")
@Api(description = "Set of endpoints for creating, retrieving, updating and deleting of keywords.")
public class KeywordController {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordController(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @GetMapping
    @ApiOperation("Returns list of all keywords.")
    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a specific keyword by their identifier. 404 if does not exist.")
    public Keyword getKeyword(@ApiParam("Id of the keyword to be obtained. Cannot be empty.")
                                  @PathVariable Long id) {

        return keywordRepository.findById(id)
                .orElseThrow(() -> new KeywordNotFoundException(id));
    }

    @PostMapping
    @ApiOperation("Creates a new keyword.")
    public Keyword addKeyword(@ApiParam("Name of a new keyword to be created.")
                                  @RequestBody String keywordName){

        return keywordRepository.findByName(keywordName)
                .orElseGet(() -> {
                    Keyword keyword = new Keyword();
                    keyword.setName(keywordName);

                    return keywordRepository.save(keyword);
                });
    }

    @PatchMapping("/{id}")
    @ApiOperation("Renames a keyword with given id.")
    public Keyword renameKeyword(@ApiParam("Id of the keyword. Cannot be empty.")
                                     @PathVariable Long id,
                                 @ApiParam(" New name of a keyword with given id.")
                                     @RequestBody String name){

        keywordRepository.findByName(name)
                .orElseThrow(() -> new KeywordAlreadyExistException("Keyword wit given name '"+ name +"' already exist"));

        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new KeywordNotFoundException(id));
        keyword.setName(name);

        return keywordRepository.save(keyword);
    }
}
