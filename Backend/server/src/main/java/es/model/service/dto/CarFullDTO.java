package es.model.service.dto;

import es.model.domain.*;

public class CarFullDTO extends ProductFullDTO {
  private Long id;

  public CarFullDTO() {
    super();
  }

  public CarFullDTO(Car car) {
    super((Product) car);
    this.id = car.getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Car toCar() {
    Car car = new Car();
    car.setId(this.getId());
    car.setId(this.getId());
    car.setName(this.getName());
    car.setLocation(this.getLocation());
    car.setDescription(this.getDescription());
    car.setState(this.getState());
    if (this.getOwner() != null) {
      car.setOwner(this.getOwner().toUser());
    }
    car.setPrice(this.getPrice());
    return car;
  }
}
