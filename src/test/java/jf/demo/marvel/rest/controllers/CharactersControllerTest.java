package jf.demo.marvel.rest.controllers;

import jf.demo.marvel.domain.MarvelCharacter;
import jf.demo.marvel.service.MarvelCharactersService;
import jf.demo.marvel.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class CharactersControllerTest {

    private static final int CHARACTER_ID = 1017100;

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
        assertEquals(CHARACTER_ID,
                     charactersController.getCharacter(CHARACTER_ID, Optional.empty()).getBody().getId());
    }

    @Test
    public void returnsNotFoundWhenCharacterDoesNotExist(){
        assertEquals(HttpStatus.NOT_FOUND,
                     charactersController.getCharacter(1, Optional.empty()).getStatusCode());
    }

    @Test
    public void canGetCharacterWithTranslatedDescription(){
        ResponseEntity<MarvelCharacter> response = charactersController.getCharacter(CHARACTER_ID,
                                                                                     Optional.of("pt"));

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("Rick Jones tem sido o melhor amigo de Hulk desde o primeiro dia," +
                         " mas agora ele é mais do que um amigo ... ele é um companheiro de equipe!" +
                         " Transformada por uma explosão de energia gama, a pele espessa e blindada da Bomba A" +
                         " é tão forte e poderosa quanto azul." +
                         " E quando ele entra em ação, ele a usa como uma bola de boliche gigante de destruição!",
                     response.getBody().getDescription());
    }

    @Test
    public void doesNotTryToTranslateOnInvalidLanguage(){
        MarvelCharactersService charactersService = mock(MarvelCharactersService.class);
        TranslationService translationService = mock(TranslationService.class);

        CharactersController charactersController = new CharactersController(charactersService, translationService);

        MarvelCharacter character = new MarvelCharacter();
        character.setDescription("A description");

        when(charactersService.getCharacter(eq(CHARACTER_ID))).thenReturn(Optional.of(character));

        ResponseEntity<MarvelCharacter> response = charactersController.getCharacter(CHARACTER_ID,
                                                                                     Optional.of("portugues"));

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verifyNoInteractions(translationService);
    }

    @Test
    public void doesNotTryToTranslateOnEmptyDescription(){
        MarvelCharactersService charactersService = mock(MarvelCharactersService.class);
        TranslationService translationService = mock(TranslationService.class);

        CharactersController charactersController = new CharactersController(charactersService, translationService);

        MarvelCharacter character = new MarvelCharacter();
        character.setDescription("");

        when(charactersService.getCharacter(eq(CHARACTER_ID))).thenReturn(Optional.of(character));

        ResponseEntity<MarvelCharacter> response = charactersController.getCharacter(CHARACTER_ID,
                                                                                     Optional.of("pt"));

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verifyNoInteractions(translationService);
    }
}