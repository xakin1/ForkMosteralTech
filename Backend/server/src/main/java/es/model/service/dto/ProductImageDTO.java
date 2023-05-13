package es.model.service.dto;

import es.model.domain.ProductImage;

public class ProductImageDTO {
	private Long id;
	private String name;
	private String type;
	private byte[] content;

	public ProductImageDTO() {
	}

	public ProductImageDTO(ProductImage productImage) {
		this.id = productImage.getId();
		this.name = productImage.getName();
		this.type = productImage.getType();
		this.content = productImage.getContent();
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

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}


	public ProductImage toProduct() {
		ProductImage productImage = new ProductImage();
		productImage.setId(this.getId());
		productImage.setName(this.getName());
		productImage.setType(this.getType());
		productImage.setContent(this.getContent());
		return productImage;
	}
}
