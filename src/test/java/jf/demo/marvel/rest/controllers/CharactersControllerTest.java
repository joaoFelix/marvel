package jf.demo.marvel.rest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CharactersControllerTest {

    @Autowired
    CharactersController charactersController;

    @Test
    public void contextLoads() {
        assertThat(charactersController).isNotNull();
    }


    @Test
    public void getAll(){
        assertEquals("Get All Characters",  charactersController.getAll());
    }

    @Test
    public void getCharacter(){
        int characterId = 1;

        assertEquals("Get Character " + characterId,  charactersController.getCharacter(characterId));
    }
}