package es.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.model.service.exceptions.AppException;
import es.model.service.exceptions.AppRuntimeException;
import es.model.service.exceptions.account.CredentialsAreNotValidException;
import es.web.rest.util.HeaderUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
  private static final Logger logger =
      LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  private ObjectMapper mapper = new ObjectMapper();

  @ExceptionHandler(AppException.class)
  public @ResponseBody ResponseEntity<Void> appExceptionHandler(AppException e) {
    String paramsJson;
    try {
      paramsJson = mapper.writeValueAsString(e.getParameters());
    } catch (JsonProcessingException ex) {
      throw new RuntimeException("Error converting parameters map", ex);
    }
    logger.debug(
        "Controlled exception. " + "Key: '" + e.getErrorCode() + "'" + "Params: " + paramsJson);

    return ResponseEntity.status(e.getStatus())
        .headers(HeaderUtil.createError(e.getErrorCode(), paramsJson))
        .build();
  }

  @ExceptionHandler(AppRuntimeException.class)
  public @ResponseBody ResponseEntity<Void> appRuntimeExceptionHandler(AppRuntimeException e) {
    logger.error(e.getMessage(), e);
    return ResponseEntity.status(e.getStatus())
        .headers(HeaderUtil.createError(e.getErrorCode(), e.getParameter()))
        .build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public @ResponseBody ResponseEntity<Void> methodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    logger.error(e.getMessage(), e);
    String paramName = e.getParameter().getParameterName();
    String typeRequiredJson = "{" + "\"type\":\"" + e.getRequiredType().getSimpleName() + "\"}";
    if (paramName.equalsIgnoreCase("id"))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(HeaderUtil.createError("invalid_id_type", typeRequiredJson))
          .build();
    else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(CredentialsAreNotValidException.class)
  public @ResponseBody ResponseEntity<Void> badCredentialsExceptionHandler(
      CredentialsAreNotValidException e) {
    logger.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .headers(HeaderUtil.createError("account.credentials_not_valid", null))
        .build();
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public @ResponseBody ResponseEntity<Void> ExpiredJwtExceptionHandler(ExpiredJwtException e) {
    logger.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .headers(HeaderUtil.createError("account.expired_JWT", null))
        .build();
  }

  @ExceptionHandler(Exception.class)
  public @ResponseBody ResponseEntity<Void> exceptionHandler(Exception e) {
    logger.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(HeaderUtil.createError("internalServerError", null))
        .build();
  }
}
