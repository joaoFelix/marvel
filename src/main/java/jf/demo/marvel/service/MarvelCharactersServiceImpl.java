package jf.demo.marvel.service;

import jf.demo.marvel.domain.MarvelCharacter;
import jf.demo.marvel.rest.auth.AuthenticationService;
import jf.demo.marvel.service.internal.MarvelResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MarvelCharactersServiceImpl implements MarvelCharactersService {

    private final String MARVEL_GATEWAY_URL = "http://gateway.marvel.com/v1/public/characters";

    private final AuthenticationService authenticationService;
    private final RestTemplate restTemplate;

    public MarvelCharactersServiceImpl(AuthenticationService authenticationService, RestTemplateBuilder tmplBuilder) {
        this.authenticationService = authenticationService;
        this.restTemplate = tmplBuilder.build();
    }

    @Override
    public StreamingResponseBody streamCharactersIds(Integer numCharacters) {
        long ts = System.currentTimeMillis();
        String url = MARVEL_GATEWAY_URL + "?offset={offset}&limit={limit}&ts={ts}&apikey={apikey}&hash={hash}";

        int limit = numCharacters != null && numCharacters > 0 && numCharacters <= 100 ? numCharacters : 100;
        int maxResults = numCharacters != null && numCharacters > 0 ? numCharacters : -1; // -1 = all results

        return outputStream -> {
            try {
                int offset = 0;
                int numResults = 0;
                boolean getMoreCharacters = true;

                outputStream.write("[".getBytes());

                while (getMoreCharacters) {
                    ResponseEntity<MarvelResponse> response = restTemplate.getForEntity(url, MarvelResponse.class,
                            offset, limit, ts, authenticationService.getPublicKey(),
                            authenticationService.getMd5HashWithTimestamp(ts));

                    if (response.getStatusCode() != HttpStatus.OK) {
                        log.warn("Did not receive Marvel Characters  {{response:{}}}", response);
                        break;
                    }

                    MarvelResponse marvelResponse = response.getBody();

                    int count = marvelResponse.getCount();

                    if(count == 0){
                        break;
                    }

                    getMoreCharacters = maxResults > 0
                            ? count > 0 && numResults + count < maxResults
                            : count > 0;

                    List<MarvelCharacter> characters = marvelResponse.getCharacters();

                    outputStream.write(Integer.toString(characters.get(0).getId()).getBytes());

                    for(int idx = 1; idx < characters.size(); idx++){
                        outputStream.write((", " + characters.get(idx).getId()).getBytes());

                        if(++numResults == maxResults){
                            break;
                        }
                    }

                    if(numResults == maxResults){
                        break;
                    }

                    offset += limit;
                }

                outputStream.write("]".getBytes());

            } catch (RestClientException e) {
                log.error("Error getting Marvel Characters", e);
                throw new IllegalStateException("Error getting Marvel Characters", e);
            }
        };
    }

    @Override
    public Optional<MarvelCharacter> getCharacter(int characterId) {
        long ts = System.currentTimeMillis();
        String url = MARVEL_GATEWAY_URL + "/{characterId}?ts={ts}&apikey={apikey}&hash={hash}";

        try {

            ResponseEntity<MarvelResponse> response = this.restTemplate.getForEntity(url, MarvelResponse.class,
                    characterId, ts, authenticationService.getPublicKey(),
                    authenticationService.getMd5HashWithTimestamp(ts));

            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.ofNullable(response.getBody().getCharacters().get(0));
            }

        } catch (RestClientException e) {
            log.error("Error getting a Marvel Character", e);
        }

        return Optional.empty();
    }
}
