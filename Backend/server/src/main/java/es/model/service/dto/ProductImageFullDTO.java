package es.model.service.dto;

import es.model.domain.Product;
import es.model.domain.ProductImage;

public class ProductImageFullDTO {
	private Long id;
	private String name;
	private String type;
	private Long size;
	private byte[] content;
	private Product product;

	public ProductImageFullDTO() {
	}

	public ProductImageFullDTO(ProductImage productImage) {
		this.id = productImage.getId();
		this.name = productImage.getName();
		this.type = productImage.getType();
		this.size = productImage.getSize();
		this.content = productImage.getContent();
		this.product = productImage.getProduct();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductImage toProduct() {
		ProductImage productImage = new ProductImage();
		productImage.setId(this.getId());
		productImage.setName(this.getName());
		productImage.setType(this.getType());
		productImage.setSize(this.getSize());
		productImage.setContent(this.getContent());
		productImage.setProduct(this.getProduct());

		return productImage;
	}
}
