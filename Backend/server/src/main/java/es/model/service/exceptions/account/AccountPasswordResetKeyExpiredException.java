package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class AccountPasswordResetKeyExpiredException extends AccountException {

  public AccountPasswordResetKeyExpiredException(String param) {
    super("account.password.reset_key_expired", param, HttpStatus.BAD_REQUEST);
    logger.warn("Account: Password reset key expired: '{}'.", param);
  }
}
