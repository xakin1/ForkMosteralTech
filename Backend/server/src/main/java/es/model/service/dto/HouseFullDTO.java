package es.model.service.dto;

import es.model.domain.*;

public class HouseFullDTO extends ProductFullDTO {
	private Long id;
	private int m2;

	public HouseFullDTO() {
		super();
	}

	public HouseFullDTO(House house) {
		super((Product) house);
		this.id = house.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getM2() {
		return m2;
	}

	public void setM2(int m2) {
		this.m2 = m2;
	}

	public House toHouse() {
		House house = new House();
		house.setId(this.getId());
		house.setId(this.getId());
		house.setName(this.getName());
		house.setLocation(this.getLocation());
		house.setDescription(this.getDescription());
		house.setState(this.getState());
		if (this.getOwner() != null) {
			house.setOwner(this.getOwner().toUser());
		}
		house.setPrice(this.getPrice());
		return house;
	}
}
