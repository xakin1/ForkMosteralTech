package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class AccountIncorrectPasswordException extends AccountException {

  public AccountIncorrectPasswordException() {
    super("account.password.incorrect_password", HttpStatus.BAD_REQUEST);
    logger.warn("Account: Incorrect password");
  }
}
