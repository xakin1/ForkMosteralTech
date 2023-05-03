package es.model.domain;

import javax.persistence.*;
import javax.persistence.Column;

@Entity(name = "t_house")
@Table(name = "t_house")
public class House extends Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  public House() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
