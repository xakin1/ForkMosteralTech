package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class CredentialsAreNotValidException extends AccountException {

  public CredentialsAreNotValidException(String errorMsg) {
    super("account.credentials_are_not_valid", errorMsg, HttpStatus.BAD_REQUEST);
    logger.warn("Account: Credentials are not valid: '{}'.", errorMsg);
  }
}
