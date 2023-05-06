package es.model.service.dto;

import es.model.domain.*;

public class UserDTO {

  private String id;
  private String name;
  private String surname;
  private String firebaseToken;

  public UserDTO() {}

  public UserDTO(AppUser user) {
    this.id = user.getId();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.firebaseToken = user.getFirebaseToken();
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

public AppUser toUser() {
    AppUser user = new AppUser();
    user.setId(this.getId());
    user.setName(this.getName());
    user.setFirebaseToken(this.getFirebaseToken());
    user.setSurname(this.getSurname());
    return user;
  }
}
