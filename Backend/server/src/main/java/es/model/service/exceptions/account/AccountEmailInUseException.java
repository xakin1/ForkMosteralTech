package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class AccountEmailInUseException extends AccountException {

  public AccountEmailInUseException(String param) {
    super("account.register.email_in_use", param, HttpStatus.BAD_REQUEST);
    logger.warn("Account: Email '{}' already in use.", param);
  }
}
