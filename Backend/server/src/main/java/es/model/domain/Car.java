package es.model.domain;

import javax.persistence.*;
import javax.persistence.Column;

@Entity(name = "t_car")
@Table(name = "t_car")
public class Car extends Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  public Car() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
