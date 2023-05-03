package es.model.domain;

import javax.persistence.*;
import javax.persistence.Column;

@Entity(name = "t_furniture")
@Table(name = "t_furniture")
public class Furniture extends Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  public Furniture() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
