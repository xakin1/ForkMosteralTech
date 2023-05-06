package es.model.service.dto;

import es.model.domain.*;

public class AppliancesFullDTO extends ProductFullDTO {
  private Long id;

  public AppliancesFullDTO() {
    super();
  }

  public AppliancesFullDTO(Appliances appliances) {
    super((Product) appliances);
    this.id = appliances.getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Appliances toAppliances() {
    Appliances appliances = new Appliances();
    appliances.setId(this.getId());
    appliances.setId(this.getId());
    appliances.setName(this.getName());
    appliances.setLocation(this.getLocation());
    appliances.setDescription(this.getDescription());
    appliances.setState(this.getState());
    if (this.getOwner() != null) {
      appliances.setOwner(this.getOwner().toUser());
    }
    appliances.setPrice(this.getPrice());
    return appliances;
  }
}
