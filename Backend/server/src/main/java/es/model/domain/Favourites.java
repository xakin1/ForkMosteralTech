package es.model.domain;

import java.time.LocalDate;
import javax.persistence.*;
import javax.persistence.Column;

@Entity(name = "t_favourites")
@Table(name = "t_favourites")
public class Favourites {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "date")
  private LocalDate date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "appuser")
  private AppUser appuser;

  public Favourites() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public AppUser getAppUser() {
    return appuser;
  }

  public void setAppUser(AppUser user) {
    this.appuser = user;
  }
}
