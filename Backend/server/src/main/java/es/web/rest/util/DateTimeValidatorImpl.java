package es.web.rest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Implementation for {@link es.web.rest.util.DateTimeValidator DateTimeValidator} interface */
public class DateTimeValidatorImpl implements DateTimeValidator {

  private DateTimeFormatter dateTimeFormatter;

  /**
   * Creates an instance of DateTimeValidatorImpl with {@link
   * java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME ISO_LOCAL_DATE_TIME} formatter by
   * default
   */
  public DateTimeValidatorImpl() {
    this.dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  }

  /**
   * Creates an instance of DateTimeValidatorImpl with given {@link
   * java.time.format.DateTimeFormatter DateTimeFormatter}
   *
   * <p>
   *
   * @param dateTimeFormatter formatter to use with
   */
  public DateTimeValidatorImpl(DateTimeFormatter dateTimeFormatter) {
    this.dateTimeFormatter = dateTimeFormatter;
  }
  /**
   * Checks, using {@link java.time.LocalDateTime LocalDateTime API}, if the given string is valid
   * according the instance formatter
   *
   * <p>
   *
   * @param dateTimeString string to check
   *     <p>
   * @return a boolean value indicating if the string is valid according instance formatter
   */
  @Override
  public boolean isValid(String dateTimeString) {
    try {
      LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }
}
