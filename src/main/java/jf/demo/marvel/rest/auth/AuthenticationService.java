package jf.demo.marvel.rest.auth;

public interface AuthenticationService {

    String getPublicKey();

    String getMd5HashWithTimestamp(long timestamp);

    String getTranslationServiceKey();
}
