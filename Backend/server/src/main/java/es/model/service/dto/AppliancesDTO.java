package es.model.service.dto;

import es.model.domain.*;

public class AppliancesDTO {

  private Long id;
  private String name;
  private String description;
  private State state;
  private UserDTO owner;
  private Double price;

  public AppliancesDTO() {}

  public AppliancesDTO(Appliances appliances) {
    this.id = appliances.getId();
    this.name = appliances.getName();
    this.description = appliances.getDescription();
    this.state = appliances.getState();
    if (appliances.getOwner() != null) {
      this.owner = new UserDTO(appliances.getOwner());
    }
    this.price = appliances.getPrice();
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public UserDTO getOwner() {
    return owner;
  }

  public void setOwner(UserDTO owner) {
    this.owner = owner;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Appliances toAppliances() {
    Appliances appliances = new Appliances();
    appliances.setId(this.getId());
    appliances.setName(this.getName());
    appliances.setDescription(this.getDescription());
    appliances.setState(this.getState());
    if (this.getOwner() != null) {
      appliances.setOwner(this.getOwner().toUser());
    }
    appliances.setPrice(this.getPrice());
    return appliances;
  }
}
