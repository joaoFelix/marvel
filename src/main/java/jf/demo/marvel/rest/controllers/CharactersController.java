package jf.demo.marvel.rest.controllers;

import jf.demo.marvel.domain.MarvelCharacter;
import jf.demo.marvel.service.MarvelCharactersService;
import jf.demo.marvel.service.TranslationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
@AllArgsConstructor
@Setter
public class CharactersController {

    private final MarvelCharactersService charactersService;
    private final TranslationService translationService;

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> getIds(@RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(charactersService.streamCharactersIds(size), HttpStatus.OK);
    }

    @GetMapping(value = "/{character_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarvelCharacter> getCharacter(@PathVariable(name="character_id") int characterId,
                                                        @RequestParam(name="language") Optional<String> language){


        return charactersService.getCharacter(characterId).map(marvelCharacter -> {
            if(language.isPresent()){
                String currentDescription = marvelCharacter.getDescription();
                Locale locale = Locale.forLanguageTag(language.get());

                //Only translates if the language is valid
                if(locale != null && !locale.toLanguageTag().equals("und") && !Strings.isEmpty(currentDescription)){
                    Optional<String> translatedDesc = translationService.translate(currentDescription,
                                                                                          "en",
                                                                                          language.get());

                    marvelCharacter.setDescription(translatedDesc.orElse(currentDescription));
                }
            }

            return new ResponseEntity<>(marvelCharacter, HttpStatus.OK);

        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
