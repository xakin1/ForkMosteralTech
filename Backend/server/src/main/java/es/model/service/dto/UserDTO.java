package es.model.service.dto;

import es.model.domain.AppUser;
import java.time.LocalDate;

public class UserDTO {

  private String id;
  private String name;
  private String surname;
  private String firebaseToken;
  private LocalDate expirationDatefirebaseToken;

  public UserDTO() {}

  public UserDTO(AppUser user) {
    this.id = user.getId();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.firebaseToken = user.getFirebaseToken();
    this.expirationDatefirebaseToken = user.getExpirationDatefirebaseToken();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getFirebaseToken() {
    return firebaseToken;
  }

  public void setFirebaseToken(String firebaseToken) {
    this.firebaseToken = firebaseToken;
  }

  public LocalDate getExpirationDatefirebaseToken() {
    return expirationDatefirebaseToken;
  }

  public void setExpirationDatefirebaseToken(LocalDate expirationDatefirebaseToken) {
    this.expirationDatefirebaseToken = expirationDatefirebaseToken;
  }

  public AppUser toUser() {
    AppUser user = new AppUser();
    user.setId(this.getId());
    user.setName(this.getName());
    user.setFirebaseToken(this.getFirebaseToken());
    user.setSurname(this.getSurname());
    user.setExpirationDatefirebaseToken(this.getExpirationDatefirebaseToken());
    return user;
  }
}
