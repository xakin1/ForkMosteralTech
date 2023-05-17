package es.model.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

  @Column(name = "firebaseToken", length = 1500)
  private String firebaseToken;

  @Column(name = "expirationDatefirebaseToken")
  private LocalDate expirationDatefirebaseToken;

  @Column(name = "location")
  private String location;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
  private List<Product> products;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "appuser")
  private List<Favourites> favourites;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
  private List<Transactions> sellerTransactions;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
  private List<Transactions> buyerTransactions;

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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
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

  public List<Transactions> getSelletTransactions() {
    return sellerTransactions;
  }

  public void setSelletTransactions(List<Transactions> selletTransactions) {
    this.sellerTransactions = selletTransactions;
  }

  public List<Transactions> getBuyerTransactions() {
    return buyerTransactions;
  }

  public void setBuyerTransactions(List<Transactions> buyerTransactions) {
    this.buyerTransactions = buyerTransactions;
  }
}
