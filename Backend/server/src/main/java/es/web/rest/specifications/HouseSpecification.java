package es.web.rest.specifications;

import es.model.domain.House;
import es.web.rest.util.specification_utils.SpecificationUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

public class HouseSpecification {
  public static Specification<House> searchAll(String search) {
    return new Specification<House>() {
      @Override
      public Predicate toPredicate(
          Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String stringToFind = ("%" + search + "%").toLowerCase();
        Path path = SpecificationUtil.getPath(root, null);
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("id").as(String.class)), stringToFind));
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
