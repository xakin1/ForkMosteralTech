package es.model.service.dto;

import java.time.LocalDate;

import es.model.domain.AppUser;

public class UserFullDTO {
  private String id;
  private String name;
  private String surname;
  private String firebaseToken;
  private LocalDate expirationDatefirebaseToken;

  private String location;

  public UserFullDTO() {}

  public UserFullDTO(AppUser user) {
    this.id = user.getId();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.location = user.getLocation();
    this.firebaseToken = user.getFirebaseToken();
    this.expirationDatefirebaseToken = user.getExpirationDatefirebaseToken();
  }

  public String getId() {
    return id;
  }

  public void String(String id) {
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
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

  public void setId(String id) {
    this.id = id;
  }

  public AppUser toUser() {
    AppUser user = new AppUser();
    user.setId(this.getId());
    user.setName(this.getName());
    user.setSurname(this.getSurname());
    user.setLocation(this.getLocation());
    user.setFirebaseToken(this.getFirebaseToken());
    user.setExpirationDatefirebaseToken(this.getExpirationDatefirebaseToken());
    return user;
  }
}
