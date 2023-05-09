package es.web.rest.util.specification_utils;

import es.web.rest.util.DateTimeValidator;
import es.web.rest.util.DateTimeValidatorImpl;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class SpecificationUtil {

  public static GenericSpecification getSpecificationFromFilters(
      List<String> filters, Boolean excluding) {
    GenericSpecification genericSpecification = new GenericSpecification();
    // We go through the entire list and apply each of the filters
    if (filters != null) {
      for (String filter : filters) {
        String[] parts = filter.split(":", 2);
        if (parts.length == 2) {
          SearchOperation op = SearchOperation.MATCH;
          Object value = parts[1];
          // if ID search, equals operation required
          if (parts[0].contains(".id")) {
            op = SearchOperation.EQUAL;
          }
          DateTimeValidator dateTimeValidator = new DateTimeValidatorImpl();
          Boolean isDateTime = dateTimeValidator.isValid(parts[1]);
          if (isDateTime) {
            value = LocalDateTime.parse(parts[1]);
            // equals operation required if filtering by date time
            op = SearchOperation.EQUAL;
          }
          if (excluding) {
            genericSpecification.add(new SearchCriteria(parts[0], value, op, LogicalOperation.OR));
          } else {
            genericSpecification.add(new SearchCriteria(parts[0], value, op, LogicalOperation.AND));
          }
        }
      }
    }
    return genericSpecification;
  }

  public static Path getPath(Root root, String path) {
    if (path == null || path.isEmpty()) {
      return root;
    }
    String[] entities = path.split("\\.");
    Path result = root;
    for (String entity : entities) {
      result = result.get(entity);
    }
    return result;
  }
}
