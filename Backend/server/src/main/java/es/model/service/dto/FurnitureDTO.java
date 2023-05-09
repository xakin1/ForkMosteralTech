package es.model.service.dto;

import es.model.domain.*;

public class FurnitureDTO {

  private Long id;
  private String name;
  private String description;
  private State state;
  private UserDTO owner;
  private Double price;

  public FurnitureDTO() {}

  public FurnitureDTO(Furniture furniture) {
    this.id = furniture.getId();
    this.name = furniture.getName();
    this.description = furniture.getDescription();
    this.state = furniture.getState();
    if (furniture.getOwner() != null) {
      this.owner = new UserDTO(furniture.getOwner());
    }
    this.price = furniture.getPrice();
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

  public Furniture toFurniture() {
    Furniture furniture = new Furniture();
    furniture.setId(this.getId());
    furniture.setName(this.getName());
    furniture.setDescription(this.getDescription());
    furniture.setState(this.getState());
    if (this.getOwner() != null) {
      furniture.setOwner(this.getOwner().toUser());
    }
    furniture.setPrice(this.getPrice());
    return furniture;
  }
}
