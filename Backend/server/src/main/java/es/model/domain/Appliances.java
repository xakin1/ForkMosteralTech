package es.model.domain;

import javax.persistence.*;
import javax.persistence.Column;

@Entity(name = "t_appliances")
@Table(name = "t_appliances")
public class Appliances extends Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  public Appliances() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
