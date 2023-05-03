package es.model.service.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public abstract class AppException extends Exception {
  private HttpStatus status;

  private String errorCode;
  private Map<String, String> parameters = null;

  /**
   * @param errorCode Message key, used in client to retrieve the error message
   * @param status Response status
   */
  public AppException(String errorCode, HttpStatus status) {
    this.status = status;
    this.errorCode = errorCode;
  }

  /**
   * @param errorCode Message key, used in client to retrieve the error message
   * @param parameter Parameter of the message. (Key is 'param')
   * @param status Response status
   */
  public AppException(String errorCode, String parameter, HttpStatus status) {
    this.status = status;
    this.errorCode = errorCode;
    parameters = new HashMap<String, String>();
    parameters.put("param", parameter);
  }

  /**
   * @param errorCode Message key, used in client to retrieve the error message
   * @param parameters Parameters of the message.
   * @param status Response status
   */
  public AppException(String errorCode, Map<String, String> parameters, HttpStatus status) {
    this.status = status;
    this.errorCode = errorCode;
    this.parameters = parameters;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }
}
