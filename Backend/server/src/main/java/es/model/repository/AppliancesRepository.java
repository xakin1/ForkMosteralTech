package es.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.model.domain.Appliances;

public interface AppliancesRepository
    extends JpaRepository<Appliances, Long>, JpaSpecificationExecutor<Appliances> {

  Optional<Appliances> findById(Long pk);

  Page<Appliances> findByIdIn(List<Long> pk, Pageable pageable);

  @Query("SELECT p AS appliances, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_appliances p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId")
  Page<AppliancesProjection> findAppliancesWithFavouritesByUserId(@Param("userId") String userId, Pageable pageable);
  public interface AppliancesProjection {
	    Appliances getAppliances();
	    Boolean getIsFavourite();
	}
}

