package jf.demo.marvel.service;

import jf.demo.marvel.domain.MarvelCharacter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;

public interface MarvelCharactersService {

    StreamingResponseBody streamCharactersIds(Integer numCharacters);

    Optional<MarvelCharacter> getCharacter(int characterId);
}
