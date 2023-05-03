package es.security;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.model.domain.user_management.UMUser;
import es.model.repository.user_management.UMUserRepository;

/** Authenticate a user from the database. */
@Component("userDetailsService")
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

  @Inject private UMUserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
    Optional<UMUser> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
    return userFromDatabase
        .map(
            user -> {
              List<GrantedAuthority> grantedAuthorities =
                  user.getAuthorities().stream()
                      .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                      .collect(Collectors.toList());
              return new org.springframework.security.core.userdetails.User(
                  lowercaseLogin, user.getPassword(), grantedAuthorities);
            })
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    "User " + lowercaseLogin + " was not found in the " + "database"));
  }
}
