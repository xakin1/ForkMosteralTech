package es.web.rest.util.specification_utils;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

  private List<SearchCriteria> list;

  public GenericSpecification() {
    this.list = new ArrayList<>();
  }

  public void add(SearchCriteria criteria) {
    list.add(criteria);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

    // create a new predicate list
    List<Predicate> andPredicates = new ArrayList<>();
    List<Predicate> orPredicates = new ArrayList<>();
    Predicate predicateAux = null;

    // add add criteria to predicates
    for (SearchCriteria criteria : list) {
      Expression<String> expression = null;
      if (criteria.getKey().contains(".")) {
        String[] keys = criteria.getKey().split("\\.");
        expression = root.get(keys[0]).get(keys[1]);
      } else {
        expression = root.get(criteria.getKey());
      }
      if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
        predicateAux = builder.greaterThan(expression, criteria.getValue().toString());
      } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
        predicateAux = builder.lessThan(expression, criteria.getValue().toString());
      } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
        predicateAux = builder.greaterThanOrEqualTo(expression, criteria.getValue().toString());
      } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
        predicateAux = builder.lessThanOrEqualTo(expression, criteria.getValue().toString());
      } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
        predicateAux = builder.notEqual(expression, criteria.getValue());
      } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
        predicateAux = builder.equal(expression, criteria.getValue());
      } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
        predicateAux =
            builder.like(
                builder.lower(expression.as(String.class)),
                "%" + criteria.getValue().toString().toLowerCase() + "%");
      } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
        predicateAux =
            builder.like(
                builder.lower(expression), criteria.getValue().toString().toLowerCase() + "%");
      } else if (criteria.getOperation().equals(SearchOperation.IN)) {
        predicateAux = builder.in(builder.lower(expression)).value(criteria.getValue().toString());
      }

      if (criteria.getLogicalOperation() == LogicalOperation.AND) {
        andPredicates.add(predicateAux);
      } else if (criteria.getLogicalOperation() == LogicalOperation.OR) {
        orPredicates.add(predicateAux);
      }
    }

    Predicate orPredicate = builder.or(orPredicates.toArray(new Predicate[0]));
    Predicate andPredicate = builder.and(andPredicates.toArray(new Predicate[0]));

    return builder.or(orPredicate, andPredicate);
  }
}
