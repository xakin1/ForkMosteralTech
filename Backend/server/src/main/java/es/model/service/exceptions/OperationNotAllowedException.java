package es.model.service.exceptions;

import org.springframework.http.HttpStatus;

public class OperationNotAllowedException extends AppException {
  public OperationNotAllowedException(String errorCode) {
    super(errorCode, HttpStatus.BAD_REQUEST);
  }
}
