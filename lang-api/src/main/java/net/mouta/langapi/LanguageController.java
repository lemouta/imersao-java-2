package net.mouta.langapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LanguageController {

    @Autowired
    private LanguageRepository repository;

    @GetMapping("languages")
    public List<Language> languageList(@RequestParam(required = false) String orderby) {
        var fieldsToBeOrderedBy = List.of("title", "ranking");
        
        if (orderby == null || !fieldsToBeOrderedBy.contains(orderby)) {
            orderby = "title";
        }
        
        return repository.findAll(Sort.by(Sort.Direction.ASC, orderby));
    }

    @GetMapping("languages/{id}")
    public Language findLanguage(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("languages")
    public ResponseEntity<Language> createLanguage(@RequestBody Language newLanguage) {
        Language language = repository.save(newLanguage);
        return new ResponseEntity<>(language, HttpStatus.CREATED);
    }

    @PutMapping("languages/{id}")
    public Language updateLanguage(@RequestBody Language newLanguage, @PathVariable String id) {
        Language language = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        language.setTitle(newLanguage.getTitle());
        language.setImage(newLanguage.getImage());
        language.setRanking(newLanguage.getRanking());

        return repository.save(language);
    }

    @DeleteMapping("languages/{id}")
    public void deleteLanguage(@PathVariable String id) {
        repository.deleteById(id);
    }

}
