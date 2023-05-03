package es.model.service.dto;

import es.model.domain.*;

public class UserDTO {

  private Long id;
  private String name;
  private String surname;

  public UserDTO() {}

  public UserDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.surname = user.getSurname();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public User toUser() {
    User user = new User();
    user.setId(this.getId());
    user.setName(this.getName());
    user.setSurname(this.getSurname());
    return user;
  }
}
