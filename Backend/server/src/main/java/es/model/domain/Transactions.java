package es.model.domain;

import java.time.LocalDate;
import javax.persistence.*;
import javax.persistence.Column;

@Entity(name = "t_transactions")
@Table(name = "t_transactions")
public class Transactions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "date")
  private LocalDate date;

  @Enumerated(EnumType.STRING)
  @Column(name = "action")
  private TransactionActions action;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  private User user;

  public Transactions() {}

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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
