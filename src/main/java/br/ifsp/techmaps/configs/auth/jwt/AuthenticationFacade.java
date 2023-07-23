package br.ifsp.techmaps.configs.auth.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UUID getUserAuthenticatedId() {
        return (UUID) getAuthentication().getPrincipal();
    }

}
