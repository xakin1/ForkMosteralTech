package es.model.repository;

import es.model.domain.House;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.model.domain.House;
import es.model.domain.Product;

public interface HouseRepository
    extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

  Optional<House> findById(Long pk);

  Page<House> findByIdIn(List<Long> pk, Pageable pageable);
  
  @Query("SELECT p, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_house p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId")
  Page<HouseProjection> findHousesWithFavouritesByUserId(@Param("userId") String userId, Pageable pageable);
  
  public interface HouseProjection {
	    House getProduct();
	    Boolean getIsFavourite();
	}
}
