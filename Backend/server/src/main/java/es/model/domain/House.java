package es.model.domain;

import javax.persistence.*;

@Entity(name = "t_house")
@Table(name = "t_house")
@PrimaryKeyJoinColumn(name = "id")
public class House extends Product {
	
	@Column(name = "m2")
	private int m2;

	public int getM2() {
		return m2;
	}

	public void setM2(int m2) {
		this.m2 = m2;
	}
}
