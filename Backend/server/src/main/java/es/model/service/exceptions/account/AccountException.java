package es.model.service.exceptions.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import es.model.service.exceptions.AppException;

public abstract class AccountException extends AppException {

  protected static final Logger logger = LoggerFactory.getLogger(AccountException.class);

  public AccountException(String errorCode, HttpStatus status) {
    super(errorCode, status);
  }

  public AccountException(String errorCode, String parameter, HttpStatus status) {
    super(errorCode, parameter, status);
  }
}
