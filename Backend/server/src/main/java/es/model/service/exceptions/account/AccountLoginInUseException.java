package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class AccountLoginInUseException extends AccountException {

  public AccountLoginInUseException(String param) {
    super("account.register.login_in_use", param, HttpStatus.BAD_REQUEST);
    logger.warn("Account: Login '{}' already in use.", param);
  }
}
