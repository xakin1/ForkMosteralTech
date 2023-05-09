package es.model.service.dto;

import es.model.domain.Transactions;
import java.time.LocalDateTime;

public class TransactionsFullDTO {
  private Long id;
  private LocalDateTime date;
  private ProductDTO product;
  private UserDTO seller;
  private UserDTO buyer;
  private Double price;
  private String name;
  private String description;

  public TransactionsFullDTO() {}

  public TransactionsFullDTO(Transactions transactions) {
    this.id = transactions.getId();
    this.date = transactions.getDate();
    this.price = transactions.getProduct().getPrice();
    this.name = transactions.getProduct().getName();
    this.description = transactions.getProduct().getDescription();

    if (transactions.getProduct() != null) {
      this.product = new ProductDTO(transactions.getProduct());
    }
    if (transactions.getBuyer() != null) {
      this.buyer = new UserDTO(transactions.getBuyer());
    }
    if (transactions.getSeller() != null) {
      this.seller = new UserDTO(transactions.getSeller());
    }
  }

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

  public ProductDTO getProduct() {
    return product;
  }

  public void setProduct(ProductDTO product) {
    this.product = product;
  }

  public UserDTO getSeller() {
    return seller;
  }

  public void setSeller(UserDTO seller) {
    this.seller = seller;
  }

  public UserDTO getBuyer() {
    return buyer;
  }

  public void setBuyer(UserDTO buyer) {
    this.buyer = buyer;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Transactions toTransactions() {
    Transactions transactions = new Transactions();
    transactions.setId(this.getId());
    transactions.setDate(this.getDate());
    transactions.setPrice(this.getPrice());
    transactions.setDescription(this.getDescription());
    transactions.setName(this.getName());
    if (this.getProduct() != null) {
      transactions.setProduct(this.getProduct().toProduct());
    }
    if (this.getBuyer() != null) {
      transactions.setBuyer(this.getBuyer().toUser());
    }

    if (this.getSeller() != null) {
      transactions.setSeller(this.getSeller().toUser());
    }
    return transactions;
  }
}
