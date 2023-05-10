package es.model.service.dto;

import es.model.domain.*;
import java.time.LocalDate;

public class FavouritesDTO {

  private Long id;
  private LocalDate date;
  private ProductDTO product;
  private UserDTO user;

  public FavouritesDTO() {}

  public FavouritesDTO(Favourites favourites) {
    this.id = favourites.getId();
    this.date = favourites.getDate();
    if (favourites.getProduct() != null) {
      this.product = new ProductDTO(favourites.getProduct(),true);
    }
    if (favourites.getAppUser() != null) {
      this.user = new UserDTO(favourites.getAppUser());
    }
  }

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

  public ProductDTO getProduct() {
    return product;
  }

  public void setProduct(ProductDTO product) {
    this.product = product;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public Favourites toFavourites() {
    Favourites favourites = new Favourites();
    favourites.setId(this.getId());
    favourites.setDate(this.getDate());
    if (this.getProduct() != null) {
      favourites.setProduct(this.getProduct().toProduct());
    }
    if (this.getUser() != null) {
      favourites.setAppUser(this.getUser().toUser());
    }
    return favourites;
  }
}
