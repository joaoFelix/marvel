package jf.demo.marvel.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jf.demo.marvel.domain.MarvelCharacter;
import jf.demo.marvel.service.MarvelCharactersService;
import jf.demo.marvel.service.TranslationService;
import lombok.AllArgsConstructor;
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
    @Operation(
        description = "Get an array of Marvel's Character ids",
        parameters = {
            @Parameter(name = "size", description = "The number of Characters ids you want to get")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Got the Characters ids",
                content = { @Content(mediaType = "application/octet-stream", examples = { @ExampleObject( value = "[1009146,1009147]") }) }),
            @ApiResponse(responseCode = "500", description = "There was an error getting Characters ids",  content = @Content())
        }
    )
    public ResponseEntity<StreamingResponseBody> getIds(@RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(charactersService.streamCharactersIds(size), HttpStatus.OK);
    }

    @GetMapping(value = "/{character_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        description = "Get info about a Marvel's Character",
        parameters = {
            @Parameter(name = "character_id", required = true, description = "The ID of the Marvel's Character",
                examples = @ExampleObject(value = "1009146")),
            @Parameter(name = "language",
                description = "The ISO-639-1 language code for the Character's description",
                examples = { @ExampleObject(value = "en"), @ExampleObject(value = "pt")})
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Got the requested Marvel Character"),
            @ApiResponse(responseCode = "404", description = "Marvel Character Not Found", content = { @Content()})
        }
    )
    public ResponseEntity<MarvelCharacter> getCharacter(@PathVariable(name="character_id") int characterId,
                                                        @RequestParam(name="language", required = false) Optional<String> language){


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
