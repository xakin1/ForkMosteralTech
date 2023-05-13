package es.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "t_productimage")
@Table(name = "t_productimage")
public class ProductImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "type")
  private String type;

  @Column(name = "size")
  private Long size;

  @Lob
  @Column(name = "content")
  private byte[] content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product")
  private Product product;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public Product getProduct() {
    return product;
  }

  public void setEntity(Product product) {
    this.product = product;
  }

public void setProduct(Product product) {
	this.product = product;
}
  
  
}
