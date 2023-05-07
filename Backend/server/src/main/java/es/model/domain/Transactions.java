package es.model.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "t_transactions")
@Table(name = "t_transactions")
@Inheritance(strategy = InheritanceType.JOINED)
public class Transactions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "date")
  private LocalDateTime date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller")
  private AppUser seller;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer")
  private AppUser buyer;

  public Transactions() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public AppUser getSeller() {
	return seller;
  }

  public void setSeller(AppUser seller) {
	this.seller = seller;
  }

  public AppUser getBuyer() {
	return buyer;
  }

  public void setBuyer(AppUser buyer) {
	this.buyer = buyer;
  }
  
  
}
