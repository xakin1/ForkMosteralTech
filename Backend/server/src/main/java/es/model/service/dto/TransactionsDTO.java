package es.model.service.dto;

import java.time.LocalDate;

import es.model.domain.*;

public class TransactionsDTO {

  private Long id;
  private LocalDate date;
  private TransactionActions action;
  private ProductDTO product;
  private UserDTO user;

  public TransactionsDTO() {}

  public TransactionsDTO(Transactions transactions) {
    this.id = transactions.getId();
    this.date = transactions.getDate();
    this.action = transactions.getAction();
    if (transactions.getProduct() != null) {
      this.product = new ProductDTO(transactions.getProduct());
    }
    if (transactions.getUser() != null) {
      this.user = new UserDTO(transactions.getUser());
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

  public TransactionActions getAction() {
    return action;
  }

  public void setAction(TransactionActions action) {
    this.action = action;
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

  public Transactions toTransactions() {
    Transactions transactions = new Transactions();
    transactions.setId(this.getId());
    transactions.setDate(this.getDate());
    transactions.setAction(this.getAction());
    if (this.getProduct() != null) {
      transactions.setProduct(this.getProduct().toProduct());
    }
    if (this.getUser() != null) {
      transactions.setUser(this.getUser().toUser());
    }
    return transactions;
  }
}
