package es.model.service.dto;

import es.model.domain.*;

public class CarDTO {

  private Long id;
  private String name;
  private String description;
  private State state;
  private UserDTO owner;
  private Double price;

  public CarDTO() {}

  public CarDTO(Car car) {
    this.id = car.getId();
    this.name = car.getName();
    this.description = car.getDescription();
    this.state = car.getState();
    if (car.getOwner() != null) {
      this.owner = new UserDTO(car.getOwner());
    }
    this.price = car.getPrice();
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

  public Car toCar() {
    Car car = new Car();
    car.setId(this.getId());
    car.setName(this.getName());
    car.setDescription(this.getDescription());
    car.setState(this.getState());
    if (this.getOwner() != null) {
      car.setOwner(this.getOwner().toUser());
    }
    return car;
  }
}
