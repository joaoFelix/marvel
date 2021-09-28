package jf.demo.marvel.rest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CharactersControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getAll(){
        ResponseEntity<String> response = template.getForEntity("/characters", String.class);

        assertEquals("Get All Characters", response.getBody());
    }

    @Test
    public void getCharacter(){
        int characterId = 1;

        ResponseEntity<String> response = template.getForEntity("/characters/" + characterId, String.class);
        assertEquals("Get Character " + characterId,  response.getBody());
    }
}