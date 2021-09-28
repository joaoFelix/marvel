package jf.demo.marvel.rest.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationServiceImplTest {

    static Authentication auth;
    static AuthenticationService authenticationService;

    @BeforeAll
    public static void setup(){
        auth = new Authentication();
        auth.setPrivateKey("private_key");
        auth.setPublicKey("public_key");

        authenticationService = new AuthenticationServiceImpl(auth);
    }

    @Test
    public void canGetPublicKey(){
        assertEquals(auth.getPublicKey(), authenticationService.getPublicKey());
    }

    @Test
    public void canGenerateMd5HashWithTimestamp(){
        long timestamp = 1L;
        String strToHash = timestamp + auth.getPrivateKey() + auth.getPublicKey();
        String expectedHash = DigestUtils.md5DigestAsHex(strToHash.getBytes());

        assertEquals(expectedHash, authenticationService.getMd5HashWithTimestamp(timestamp));
    }

}