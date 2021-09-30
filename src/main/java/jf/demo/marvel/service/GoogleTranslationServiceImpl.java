package jf.demo.marvel.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.testing.RemoteTranslateHelper;
import jf.demo.marvel.rest.auth.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

@Service
@Log4j2
public class GoogleTranslationServiceImpl implements TranslationService {

    public static final String GOOGLE_PROJECT_ID = "263630984610";

    private final Translate translate;

    public GoogleTranslationServiceImpl(AuthenticationService authenticationService) throws FileNotFoundException {

        RemoteTranslateHelper helper = RemoteTranslateHelper.create(
            GOOGLE_PROJECT_ID,
            new FileInputStream(authenticationService.getTranslationServiceKey())
        );

        translate = helper.getOptions().getService();
    }

    @Override
    public Optional<String> translate(String text, String fromLang, String toLang) {
        try {
            Translation translation =
                translate.translate(
                    text,
                    Translate.TranslateOption.sourceLanguage(fromLang),
                    Translate.TranslateOption.targetLanguage(toLang),
                    Translate.TranslateOption.model("base"));

            return Optional.ofNullable(translation.getTranslatedText());

        } catch (Exception e) {
            log.error("Error translating", e);
        }

        return Optional.empty();
    }
}
