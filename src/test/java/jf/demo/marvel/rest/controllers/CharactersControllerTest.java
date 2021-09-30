package jf.demo.marvel.rest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
    public void canGetAll() {
        ResponseEntity<StreamingResponseBody> response = charactersController.getIds(1);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void canGetCharacter(){
        int idFor3DMan = 1011334;

        assertEquals(idFor3DMan,  charactersController.getCharacter(idFor3DMan).getBody().getId());
    }

    @Test
    public void returnsNotFoundWhenCharacterDoesNotExist(){
        assertEquals(HttpStatus.NOT_FOUND,  charactersController.getCharacter(1).getStatusCode());
    }
}