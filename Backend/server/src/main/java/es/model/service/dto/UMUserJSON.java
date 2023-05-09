package es.model.service.dto;

import es.config.Constants;
import es.model.domain.user_management.Authority;
import es.model.domain.user_management.UMUser;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/** A DTO representing a user, with his authorities. */
public class UMUserJSON {

  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  private String login;

  @Size(max = 50)
  private String firstName;

  @Size(max = 50)
  private String lastName;

  @Email
  @Size(min = 5, max = 100)
  private String email;

  @Size(min = 2, max = 5)
  private String langKey;

  private Set<String> authorities;

  public UMUserJSON() {}

  public UMUserJSON(UMUser user) {
    this(
        user.getLogin(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getLangKey(),
        user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
  }

  public UMUserJSON(
      String login,
      String firstName,
      String lastName,
      String email,
      String langKey,
      Set<String> authorities) {
    this.login = login;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.langKey = langKey;
    this.authorities = authorities;
  }

  public String getLogin() {
    return login;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getLangKey() {
    return langKey;
  }

  public Set<String> getAuthorities() {
    return authorities;
  }

  @Override
  public String toString() {
    return "UMUserJSON{"
        + "login='"
        + login
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", langKey='"
        + langKey
        + '\''
        + ", authorities="
        + authorities
        + "}";
  }
}
