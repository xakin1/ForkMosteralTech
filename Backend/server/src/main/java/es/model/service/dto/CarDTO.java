package es.model.service.dto;

import es.model.domain.Car;
import es.model.domain.State;
import es.model.repository.CarRepository.CarProjection;

public class CarDTO {

	private Long id;
	private String name;
	private String description;
	private State state;
	private UserDTO owner;
	private Double price;
	private Boolean isFavourite;

	public CarDTO() {
	}

	public CarDTO(CarProjection product) {
		this(product.getProduct());
		this.isFavourite = product.getIsFavourite();
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public Car toCar() {
		Car car = new Car();
		car.setId(this.getId());
		car.setName(this.getName());
		car.setDescription(this.getDescription());
		car.setState(this.getState());
		car.setPrice(this.getPrice());
		if (this.getOwner() != null) {
			car.setOwner(this.getOwner().toUser());
		}
		return car;
	}
}
