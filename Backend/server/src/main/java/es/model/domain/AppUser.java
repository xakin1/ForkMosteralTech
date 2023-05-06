package es.model.domain;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Column;
import org.locationtech.jts.geom.Point;

@Entity(name = "appuser")
@Table(name = "appuser")
public class AppUser {
  @Id
  @Column(name = "id", unique = true)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;
  
  @Column(name = "firebaseToken", length =1500)
  private String firebaseToken;
  
  @Column(name = "expirationDatefirebaseToken")
  private LocalDate expirationDatefirebaseToken;

  @Column(name = "location", columnDefinition = "geometry(Point, 4326)")
  private Point location;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
  private List<Product> products;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "appuser")
  private List<Favourites> favourites;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "appuser")
  private List<Transactions> transactions;

  public AppUser() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Point getLocation() {
    return location;
  }

  public void setLocation(Point location) {
    this.location = location;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public List<Favourites> getFavourites() {
    return favourites;
  }

  public void setFavourites(List<Favourites> favourites) {
    this.favourites = favourites;
  }

  public List<Transactions> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transactions> transactions) {
    this.transactions = transactions;
  }

  public String getFirebaseToken() {
	return firebaseToken;
  }

  public void setFirebaseToken(String firebaseToken) {
	this.firebaseToken = firebaseToken;
  }

  public LocalDate getExpirationDatefirebaseToken() {
	return expirationDatefirebaseToken;
  }

  public void setExpirationDatefirebaseToken(LocalDate expirationDatefirebaseToken) {
	this.expirationDatefirebaseToken = expirationDatefirebaseToken;
  }
}
