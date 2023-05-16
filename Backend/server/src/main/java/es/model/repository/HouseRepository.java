package es.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.model.domain.House;
import es.model.domain.State;

public interface HouseRepository
    extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

  Optional<House> findById(Long pk);

  @Query("SELECT p AS product, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_house p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId LEFT JOIN t_product as pr ON p.id = pr.id WHERE p.price BETWEEN :minPrice and :maxPrice and p.m2 BETWEEN :minm2 and :maxm2 and (:state IS NULL OR pr.state = :state)")
  Page<HouseProjection> findHousesWithFavouritesByUserId(@Param("userId") String userId, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("minm2") Integer minm2, @Param("maxm2") Integer maxm2, @Param("state") State state, Pageable pageable);
  
  public interface HouseProjection {
	    House getProduct();
	    Boolean getIsFavourite();
	}
}
