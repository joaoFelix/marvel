package jf.demo.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MarvelCharacter {

    private int id;
    private String name;
    private String description;
    private Thumbnail thumbnail;

    @Getter @Setter @ToString
    private static class Thumbnail {

        private String path;
        private String extension;
    }
}
