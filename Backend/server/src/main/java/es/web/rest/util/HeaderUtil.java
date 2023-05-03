package es.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class HeaderUtil {
  private static final Logger logger = LoggerFactory.getLogger(HeaderUtil.class);

  /*
   * errorKey must be the path to access the i18n message value at client side
   */
  public static HttpHeaders createError(String errorKey, String params) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Access-Control-Expose-Headers", "X-app-error");
    headers.add("Access-Control-Expose-Headers", "X-app-params");
    headers.add("X-app-error", errorKey);
    headers.add("X-app-params", params);
    return headers;
  }
}
