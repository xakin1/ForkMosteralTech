package es.model.service.dto;

import es.model.domain.House;
import es.model.domain.State;
import es.model.repository.HouseRepository.HouseProjection;

public class HouseDTO {

  private Long id;
  private String name;
  private String description;
  private State state;
  private UserDTO owner;
  private Double price;
  private boolean isFavourite;
  public HouseDTO() {}
  
  public HouseDTO(HouseProjection product) {
    this(product.getProduct());
    this.isFavourite = product.getIsFavourite();
  }

  public HouseDTO(House house) {
    this.id = house.getId();
    this.name = house.getName();
    this.description = house.getDescription();
    this.state = house.getState();
    this.price = house.getPrice();
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
  
  

public boolean isFavourite() {
	return isFavourite;
}

public void setIsFavourite(boolean isFavourite) {
	this.isFavourite = isFavourite;
}

public House toHouse() {
    House house = new House();
    house.setId(this.getId());
    house.setName(this.getName());
    house.setDescription(this.getDescription());
    house.setState(this.getState());
    house.setPrice(this.getPrice());

    return house;
  }
}
