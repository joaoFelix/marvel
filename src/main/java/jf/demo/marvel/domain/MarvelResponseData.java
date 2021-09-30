package jf.demo.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class MarvelResponseData {

    private Integer count;
    private List<MarvelCharacter> results;
}
