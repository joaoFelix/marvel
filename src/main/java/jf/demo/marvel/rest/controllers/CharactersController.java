package jf.demo.marvel.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharactersController {

    @GetMapping("/characters")
    public String get(){
        return "Get All Characters!";
    }

    @GetMapping("/characters/{character_id}")
    public String getCharacter(@PathVariable(name="character_id") Integer characterId){
        return "Get Character " + characterId;
    }
}
