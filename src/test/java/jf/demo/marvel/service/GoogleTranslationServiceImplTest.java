package jf.demo.marvel.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GoogleTranslationServiceImplTest {

    @Autowired
    private GoogleTranslationServiceImpl translationService;

    @Test
    public void contextLoads() {
        assertThat(translationService).isNotNull();
    }

    @Test
    public void canTranslate() {
        Optional<String> translate = translationService.translate("Hello World", "en", "pt");

        assertTrue(translate.isPresent());
        assertEquals("Olá Mundo", translate.get());
    }


}