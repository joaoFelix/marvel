package jf.demo.marvel.rest.auth;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final Authentication authentication;

    public AuthenticationServiceImpl(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public String getPublicKey() {
        return authentication.getPublicKey();
    }

    @Override
    public String getMd5HashWithTimestamp(long timestamp) {
        String hashBase = timestamp + authentication.getPrivateKey() + authentication.getPublicKey();

        return DigestUtils.md5DigestAsHex(hashBase.getBytes());
    }
}
