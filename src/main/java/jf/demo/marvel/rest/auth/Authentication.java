package jf.demo.marvel.rest.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
public class Authentication {

    @Value("${marvel.publicKey}")
    private String publicKey;

    @Value("${marvel.privateKey}")
    private String privateKey;

    @Value("${translation.key}")
    private String translationServiceKey;
}
