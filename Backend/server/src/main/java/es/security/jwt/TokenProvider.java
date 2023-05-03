package es.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import es.config.SecurityProperties;

@Component
public class TokenProvider {
  private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

  private static final String AUTHORITIES_KEY = "auth";

  @Inject private SecurityProperties properties;

  public String createToken(Authentication authentication, Boolean rememberMe) {
    String authority = authentication.getAuthorities().iterator().next().toString();

    long now = (new Date()).getTime();
    Date validity = null;
    if (rememberMe) {
      validity = new Date(now + (properties.getSecurity().getJwtValidity() * 1000));
    } else {
      // If user does not select rememberMe, token is saved only 60 seconds
      validity = new Date(now + (properties.getSecurity().getShortJwtValidity() * 1000));
    }

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authority)
        .signWith(SignatureAlgorithm.HS512, properties.getSecurity().getJwtSecretKey())
        .setExpiration(validity)
        .compact();
  }

  public Authentication getAuthentication(String authToken) {
    Claims claims =
        Jwts.parser()
            .setSigningKey(properties.getSecurity().getJwtSecretKey())
            .parseClaimsJws(authToken)
            .getBody();
    GrantedAuthority authority = new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY).toString());
    Collection<GrantedAuthority> authorities = Collections.singleton(authority);

    String principal = claims.getSubject();

    return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
  }

  public boolean validateToken(String authToken) {
    Jwts.parser()
        .setSigningKey(properties.getSecurity().getJwtSecretKey())
        .parseClaimsJws(authToken);
    return true;
  }
}
