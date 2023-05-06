package es.model.service.dto;

import es.model.domain.*;

public class FurnitureFullDTO extends ProductFullDTO {
  private Long id;

  public FurnitureFullDTO() {
    super();
  }

  public FurnitureFullDTO(Furniture furniture) {
    super((Product) furniture);
    this.id = furniture.getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Furniture toFurniture() {
    Furniture furniture = new Furniture();
    furniture.setId(this.getId());
    furniture.setId(this.getId());
    furniture.setName(this.getName());
    furniture.setLocation(this.getLocation());
    furniture.setDescription(this.getDescription());
    furniture.setState(this.getState());
    if (this.getOwner() != null) {
      furniture.setOwner(this.getOwner().toUser());
    }
    furniture.setPrice(this.getPrice());
    return furniture;
  }
}
