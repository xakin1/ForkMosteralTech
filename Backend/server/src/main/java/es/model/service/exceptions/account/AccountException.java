package es.model.service.exceptions.account;

import es.model.service.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public abstract class AccountException extends AppException {

  protected static final Logger logger = LoggerFactory.getLogger(AccountException.class);

  public AccountException(String errorCode, HttpStatus status) {
    super(errorCode, status);
  }

  public AccountException(String errorCode, String parameter, HttpStatus status) {
    super(errorCode, parameter, status);
  }
}
