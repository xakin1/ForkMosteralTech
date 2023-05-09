package es.web.rest.custom.user_management.vm;

import es.model.domain.user_management.UMUser;
import es.model.service.dto.UMUserJSON;
import java.time.ZonedDateTime;
import java.util.Set;
import javax.validation.constraints.Size;

/** View Model extending the UserDTO, which is meant to be used in the user management UI. */
public class UserJSON extends UMUserJSON {

  public static final int PASSWORD_MIN_LENGTH = 4;
  public static final int PASSWORD_MAX_LENGTH = 100;

  private Long id;

  private String createdBy;

  private ZonedDateTime createdDate;

  private String lastModifiedBy;

  private ZonedDateTime lastModifiedDate;

  @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
  private String password;

  public UserJSON() {}

  public UserJSON(UMUser user) {
    super(user);
    this.id = user.getId();
    this.createdBy = user.getCreatedBy();
    this.createdDate = user.getCreatedDate();
    this.lastModifiedBy = user.getLastModifiedBy();
    this.lastModifiedDate = user.getLastModifiedDate();
    this.password = null;
  }

  public UserJSON(
      Long id,
      String login,
      String password,
      String firstName,
      String lastName,
      String email,
      String langKey,
      Set<String> authorities,
      String createdBy,
      ZonedDateTime createdDate,
      String lastModifiedBy,
      ZonedDateTime lastModifiedDate) {
    super(login, firstName, lastName, email, langKey, authorities);
    this.id = id;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedDate = lastModifiedDate;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public ZonedDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(ZonedDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public ZonedDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "UserJSON{"
        + "id="
        + id
        + ", createdBy="
        + createdBy
        + ", createdDate="
        + createdDate
        + ", lastModifiedBy='"
        + lastModifiedBy
        + '\''
        + ", lastModifiedDate="
        + lastModifiedDate
        + "} "
        + super.toString();
  }
}
