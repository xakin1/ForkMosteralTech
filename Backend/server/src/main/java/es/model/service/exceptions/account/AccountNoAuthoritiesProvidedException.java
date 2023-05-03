package es.model.service.exceptions.account;

import org.springframework.http.HttpStatus;

public class AccountNoAuthoritiesProvidedException extends AccountException {

  public AccountNoAuthoritiesProvidedException() {
    super("account.not_authorities_provided", HttpStatus.BAD_REQUEST);
    logger.warn("Account: No authorities provided");
  }
}
