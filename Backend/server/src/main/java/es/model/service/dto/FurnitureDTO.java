package es.model.service.dto;

import es.model.domain.Furniture;
import es.model.domain.State;
import es.model.repository.FurnitureRepository.FurnitureProjection;

public class FurnitureDTO {

	private Long id;
	private String name;
	private String description;
	private State state;
	private UserDTO owner;
	private Double price;
	private boolean isFavourite;
	private ProductOwner productOwner;

	public FurnitureDTO() {
	}

	public FurnitureDTO(FurnitureProjection product) {
		this(product.getProduct());
		this.isFavourite = product.getIsFavourite();
	}

	public FurnitureDTO(Furniture furniture) {
		this.id = furniture.getId();
		this.name = furniture.getName();
		this.description = furniture.getDescription();
		this.state = furniture.getState();
		this.price = furniture.getPrice();
		this.productOwner = new ProductOwner(furniture.getOwner().getId(), furniture.getOwner().getName(),furniture.getOwner().getSurname());

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

	public Furniture toFurniture() {
		Furniture furniture = new Furniture();
		furniture.setId(this.getId());
		furniture.setName(this.getName());
		furniture.setDescription(this.getDescription());
		furniture.setState(this.getState());
		furniture.setPrice(this.getPrice());
		return furniture;
	}

	public ProductOwner getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(ProductOwner productOwner) {
		this.productOwner = productOwner;
	}
}
