package jf.demo.marvel.rest.controllers;

import jf.demo.marvel.rest.auth.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharactersController {

    private Authentication authentication;

    public CharactersController(Authentication authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/characters")
    public String getAll(){
        return "Get All Characters";
    }

    @GetMapping("/characters/{character_id}")
    public String getCharacter(@PathVariable(name="character_id") Integer characterId){
        return "Get Character " + characterId;
    }
}
