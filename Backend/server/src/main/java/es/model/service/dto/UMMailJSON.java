package es.model.service.dto;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

public class UMMailJSON {

  @Email
  @Size(min = 5, max = 100)
  private String email;

  public UMMailJSON() {}

  public UMMailJSON(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
