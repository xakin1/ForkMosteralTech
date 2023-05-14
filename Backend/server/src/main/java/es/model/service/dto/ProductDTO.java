package es.model.service.dto;

import es.model.domain.Product;
import es.model.domain.State;
import es.model.repository.ProductRepository.ProductProjection;

public class ProductDTO {

	private Long id;
	private String name;
	private String description;
	private Double price;
	private ProductOwner productOwner;
	private State state;
	private boolean isFavourite;

	public ProductDTO() {
	}

	public ProductDTO(ProductProjection product) {
		this(product.getProduct());
		this.isFavourite = product.getIsFavourite();
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.state = product.getState();
		this.price = product.getPrice();
		if(product.getOwner() != null)
			this.productOwner = new ProductOwner(product.getOwner().getId(), product.getOwner().getName(),product.getOwner().getSurname());
	}

	public ProductDTO(Product product, boolean isFavourite) {
		this(product);
		this.isFavourite = isFavourite;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public ProductOwner getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(ProductOwner productOwner) {
		this.productOwner = productOwner;
	}

	public Product toProduct() {
		Product product = new Product();
		product.setId(this.getId());
		product.setName(this.getName());
		product.setDescription(this.getDescription());
		product.setState(this.getState());
		product.setPrice(this.getPrice());
		
		return product;
	}
}
