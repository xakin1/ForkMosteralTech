package es.model.domain;

import java.util.List;
import javax.persistence.*;
import javax.persistence.Column;
import org.locationtech.jts.geom.Point;

@Entity(name = "t_product")
@Table(name = "t_product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "location", columnDefinition = "geometry(Point, 4326)")
  private Point location;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  private State state;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
  private List<Transactions> transactions;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner")
  private User owner;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
  private List<Favourites> favourites;

  public Product() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Point getLocation() {
    return location;
  }

  public void setLocation(Point location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public List<Transactions> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transactions> transactions) {
    this.transactions = transactions;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public List<Favourites> getFavourites() {
    return favourites;
  }

  public void setFavourites(List<Favourites> favourites) {
    this.favourites = favourites;
  }
}
