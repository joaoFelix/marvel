package jf.demo.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class MarvelResponse {

    private MarvelResponseData data;

    public Integer getCount(){
        return data.getCount();
    }

    public List<MarvelCharacter> getCharacters() {
        return data.getResults();
    }
}
