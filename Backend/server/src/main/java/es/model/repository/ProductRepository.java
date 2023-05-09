package es.model.repository;

import es.model.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  Optional<Product> findById(Long pk);

  Page<Product> findByIdIn(List<Long> pk, Pageable pageable);

  @Query("SELECT t FROM t_product t WHERE t.owner.id = :userId")
  Page<Product> findByUserId(@Param("userId") String userId, Pageable pageable);
  
  @Query("SELECT p as product, CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavourite FROM t_product p LEFT JOIN t_favourites f ON f.product.id = p.id AND f.appuser.id = :userId")

  Page<ProductProjection> findProductsWithFavouritesByUserId(@Param("userId") String userId, Pageable pageable);
  public interface ProductProjection {
	    Product getProduct();
	    Boolean getIsFavourite();
	}
}
