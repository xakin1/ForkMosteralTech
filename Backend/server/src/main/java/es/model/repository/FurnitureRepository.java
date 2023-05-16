package es.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.model.domain.Furniture;
import es.model.domain.State;

public interface FurnitureRepository
    extends JpaRepository<Furniture, Long>, JpaSpecificationExecutor<Furniture> {

  Optional<Furniture> findById(Long pk);

  Page<Furniture> findByIdIn(List<Long> pk, Pageable pageable);
  
  @Query("SELECT p AS product, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_furniture p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId LEFT JOIN t_product as pr ON p.id = pr.id WHERE p.price BETWEEN :minPrice and :maxPrice and (:state IS NULL OR pr.state = :state)")
  Page<FurnitureProjection> findFurnituresWithFavouritesByUserId(@Param("userId") String userId, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("state") State state, Pageable pageable);
  
  public interface FurnitureProjection {
	    Furniture getProduct();
	    Boolean getIsFavourite();
	}
}
