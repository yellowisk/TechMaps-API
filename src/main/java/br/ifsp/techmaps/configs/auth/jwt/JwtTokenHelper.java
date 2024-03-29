package br.ifsp.techmaps.configs.auth.jwt;

import br.ifsp.techmaps.domain.entities.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtTokenHelper {
    public static final int MILLIS_OF_MINUTES = 60000;
    public static final int SECONDS_OF_DAY = 86400;
    private final SecretKey secretKey;

    private final JwtProperties jwtProperties;

    public JwtTokenHelper(SecretKey secretKey, JwtProperties jwtProperties) {
        this.secretKey = secretKey;
        this.jwtProperties = jwtProperties;
    }

    public String createAccessToken(User user, String issuer) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .setExpiration(getAccessTokenExpiration())
                .signWith(secretKey)
                .compact();
    }

    private Date getAccessTokenExpiration() {
        return new Date(System.currentTimeMillis() + jwtProperties.getTokenExpirationAfterMinutes() * MILLIS_OF_MINUTES);
    }

    public Cookie createRefreshTokenCookie(User user, String issuer) {
        final String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .setExpiration(getRefreshTokenExpiration())
                .signWith(secretKey)
                .compact();

        Cookie cookie = new Cookie(jwtProperties.getRefreshTokenProperty(), token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(getCookieExpiration());
        cookie.setPath("/");
        return cookie;
    }

    private java.sql.Date getRefreshTokenExpiration() {
        return java.sql.Date.valueOf(LocalDate.now().plusDays(jwtProperties.getRefreshTokenExpirationAfterDays()));
    }

    private int getCookieExpiration() {
        return jwtProperties.getRefreshTokenExpirationAfterDays() * SECONDS_OF_DAY;
    }

    public void configureTokenErrorResponse(HttpServletResponse response, String message) throws IOException {
        final String headerError = jwtProperties.getTokenPrefix() + " error=" + message;
        response.addHeader(jwtProperties.getAuthorizationHeader(), headerError);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, String> error = new HashMap<>();
        error.put("errorDescription", message);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    public boolean hasInvalidAuthorization(String authorizationHeader) {
        return Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ");
    }

    public Claims extractClaims(String string) {
        final Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(string);
        return claimsJws.getBody();
    }

}
