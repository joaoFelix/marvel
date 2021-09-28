package jf.demo.marvel.rest.controllers;

import jf.demo.marvel.rest.auth.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharactersController {

    private final AuthenticationService authenticationService;

    public CharactersController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
