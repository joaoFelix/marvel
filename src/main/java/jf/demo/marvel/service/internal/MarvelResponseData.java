package jf.demo.marvel.service.internal;

import jf.demo.marvel.domain.MarvelCharacter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class MarvelResponseData {

    private Integer count;
    private List<MarvelCharacter> results;
}
