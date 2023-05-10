package es.model.repository;

import es.model.domain.Car;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.model.domain.Car;
import es.model.domain.House;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

  Optional<Car> findById(Long pk);

  Page<Car> findByIdIn(List<Long> pk, Pageable pageable);
  
  @Query("SELECT p AS product, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_car p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId")
  Page<CarProjection> findCarsWithFavouritesByUserId(@Param("userId") String userId, Pageable pageable);
  
  public interface CarProjection {
	  Car getProduct();
	  Boolean getIsFavourite();
	}
}
