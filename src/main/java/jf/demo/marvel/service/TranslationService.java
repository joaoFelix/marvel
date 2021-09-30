package jf.demo.marvel.service;

import java.util.Optional;

public interface TranslationService {

    Optional<String> translate(String text, String fromLang, String toLang);
}
