package es.model.service.dto;

import es.model.domain.Product;
import es.model.domain.State;

public class ProductFullDTO {
  private Long id;
  private String name;

  private String location;

  private String description;
  private State state;
  private UserDTO owner;
  private Double price;

  public ProductFullDTO() {}

  public ProductFullDTO(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.location = product.getLocation();
    this.description = product.getDescription();
    this.state = product.getState();
    if (product.getOwner() != null) {
      this.owner = new UserDTO(product.getOwner());
    }
    this.price = product.getPrice();
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
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

  public Product toProduct() {
    Product product = new Product();
    product.setId(this.getId());
    product.setName(this.getName());
    product.setLocation(this.getLocation());
    product.setDescription(this.getDescription());
    product.setState(this.getState());
    if (this.getOwner() != null) {
      product.setOwner(this.getOwner().toUser());
    }
    product.setPrice(this.getPrice());
    return product;
  }
}
