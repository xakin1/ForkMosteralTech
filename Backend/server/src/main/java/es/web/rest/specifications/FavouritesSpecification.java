package es.web.rest.specifications;

import es.model.domain.Favourites;
import es.web.rest.util.specification_utils.SpecificationUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

public class FavouritesSpecification {
  public static Specification<Favourites> searchAll(String search) {
    return new Specification<Favourites>() {
      @Override
      public Predicate toPredicate(
          Root<Favourites> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String stringToFind = ("%" + search + "%").toLowerCase();
        Path path = SpecificationUtil.getPath(root, null);
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("id").as(String.class)), stringToFind));
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
