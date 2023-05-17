package es.model.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  @Column(name = "price")
  private Double price;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  private State state;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller")
  private AppUser seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer")
  private AppUser buyer;

  @Column(name = "location")
  private String location;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
