package es.web.rest.custom;

import java.util.List;

public class ValidationError {

  private String entity;
  private List<ValidationFieldError> fieldErrors;

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public List<ValidationFieldError> getFieldErrors() {
    return fieldErrors;
  }

  public void setFieldErrors(List<ValidationFieldError> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }
}
