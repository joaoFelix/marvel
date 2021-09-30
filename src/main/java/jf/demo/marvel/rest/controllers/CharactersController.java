package jf.demo.marvel.rest.controllers;

import jf.demo.marvel.domain.MarvelCharacter;
import jf.demo.marvel.service.MarvelCharactersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;


@RestController
@RequestMapping("/characters")
@AllArgsConstructor
public class CharactersController {

    private final MarvelCharactersService service;

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> getIds(@RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(service.streamCharactersIds(size), HttpStatus.OK);
    }

    @GetMapping(value = "/{character_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarvelCharacter> getCharacter(@PathVariable(name="character_id") int characterId){

        Optional<MarvelCharacter> character = service.getCharacter(characterId);

        return character.map(marvelCharacter -> new ResponseEntity<>(marvelCharacter, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
