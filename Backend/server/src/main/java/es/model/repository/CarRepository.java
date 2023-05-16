package es.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.model.domain.Car;
import es.model.domain.State;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

  Optional<Car> findById(Long pk);

  Page<Car> findByIdIn(List<Long> pk, Pageable pageable);
  
  @Query("SELECT p AS product, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_car p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId LEFT JOIN t_product as pr ON p.id = pr.id WHERE p.price BETWEEN :minPrice and :maxPrice and p.km BETWEEN :minKm and :maxKm and (:state IS NULL OR pr.state = :state)")
  Page<CarProjection> findCarsWithFavouritesByUserId(@Param("userId") String userId, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("minKm") Integer minKm, @Param("maxKm") Integer maxKm, @Param("state") State state, Pageable pageable);
  
  public interface CarProjection {
	  Car getProduct();
	  Boolean getIsFavourite();
	}
}
