package es.web.rest.specifications;

import es.model.domain.Transactions;
import es.web.rest.util.specification_utils.SpecificationUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TransactionsSpecification {
  public static Specification<Transactions> searchAll(String search) {
    return new Specification<Transactions>() {
      @Override
      public Predicate toPredicate(
          Root<Transactions> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String stringToFind = ("%" + search + "%").toLowerCase();
        Path path = SpecificationUtil.getPath(root, null);
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("id").as(String.class)), stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("action").as(String.class)), stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(
                    root.join("product", JoinType.LEFT).get("id").as(String.class)),
                stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(root.join("user", JoinType.LEFT).get("id").as(String.class)),
                stringToFind));
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
