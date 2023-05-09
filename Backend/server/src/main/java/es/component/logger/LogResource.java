package es.component.logger;

import es.web.rest.custom.ValidationErrorUtils;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint created to receive WARNs and ERRORs from client and log them into the server's logger
 * The message and level attributes are MANDATORY
 */
@RestController
@RequestMapping(LogResource.LOG_RESOURCE_URL)
public class LogResource {

  public static final String LOG_RESOURCE_URL = "/api/logs";

  private static final String messageFormat = "{}";

  private static final Logger logger = LoggerFactory.getLogger(LogResource.class);

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Log log, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }

    switch (log.getLevel()) {
      case DEBUG:
        logger.debug(messageFormat, log.getMessage());
        break;
      case INFO:
        logger.info(messageFormat, log.getMessage());
        break;
      case WARN:
        logger.warn(messageFormat, log.getMessage());
        break;
      case ERROR:
        logger.error(messageFormat, log.getMessage());
        break;
      case TRACE:
        logger.info(messageFormat, log.getMessage());
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
