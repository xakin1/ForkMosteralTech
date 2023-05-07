package es.model.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import es.model.domain.Transactions;

public class TransactionsFullDTO {
  private Long id;
  private LocalDateTime date;
  private ProductDTO product;
  private UserDTO seller;
  private UserDTO buyer;

  public TransactionsFullDTO() {}

  public TransactionsFullDTO(Transactions transactions) {
    this.id = transactions.getId();
    this.date = transactions.getDate();
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

  public Transactions toTransactions() {
    Transactions transactions = new Transactions();
    transactions.setId(this.getId());
    transactions.setDate(this.getDate());
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
