package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class AccountEmailNotRegisteredException extends AccountException {

  public AccountEmailNotRegisteredException(String param) {
    super("account.email_not_registered", param, HttpStatus.BAD_REQUEST);
    logger.warn("Account: Email '{}' not registered.", param);
  }
}
