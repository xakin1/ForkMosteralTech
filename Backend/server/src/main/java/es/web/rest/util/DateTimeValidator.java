package es.web.rest.util;

public interface DateTimeValidator {
  /**
   * Checks if the given string is a valid date time
   *
   * <p>
   *
   * @param dateTimeString string to check
   *     <p>
   * @return a boolean value indicating if the string is valid
   */
  boolean isValid(String dateTimeString);
}
