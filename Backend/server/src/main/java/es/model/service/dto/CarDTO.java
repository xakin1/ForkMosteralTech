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
	private boolean isFavourite;
	private ProductOwner productOwner;
	private int km;

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
		this.price = car.getPrice();
		this.km = car.getKm();
		this.productOwner = new ProductOwner(car.getOwner().getId(), car.getOwner().getName(),
				car.getOwner().getSurname());

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

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public Car toCar() {
		Car car = new Car();
		car.setId(this.getId());
		car.setName(this.getName());
		car.setDescription(this.getDescription());
		car.setState(this.getState());
		car.setPrice(this.getPrice());
		this.setKm(this.km);
		return car;
	}

	public ProductOwner getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(ProductOwner productOwner) {
		this.productOwner = productOwner;
	}
}
