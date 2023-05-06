package es.model.service.dto;

import java.time.LocalDate;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.model.domain.AppUser;
import es.model.util.jackson.CustomGeometryDeserializer;
import es.model.util.jackson.CustomGeometrySerializer;

public class UserFullDTO {
  private String id;
  private String name;
  private String surname;
  private String firebaseToken;
  private LocalDate expirationDatefirebaseToken;
  
  @JsonSerialize(using = CustomGeometrySerializer.class)
  @JsonDeserialize(using = CustomGeometryDeserializer.class)
  private Point location;

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

  public Point getLocation() {
    return location;
  }

  public void setLocation(Point location) {
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
