package es.web.rest.specifications;

import es.model.domain.ProductImage;
import es.web.rest.util.specification_utils.SpecificationUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

public class ProductImageSpecification {
  public static Specification<ProductImage> searchAll(String search) {
    return new Specification<ProductImage>() {
      @Override
      public Predicate toPredicate(
          Root<ProductImage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String stringToFind = ("%" + search + "%").toLowerCase();
        Path path = SpecificationUtil.getPath(root, null);
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("id").as(String.class)), stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("name").as(String.class)), stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("type").as(String.class)), stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(path.get("size").as(Long.class)), stringToFind));
        predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(path.get("content").as(byte.class)), stringToFind));
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(root.join("product", JoinType.LEFT).get("id").as(String.class)),
                stringToFind));
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
