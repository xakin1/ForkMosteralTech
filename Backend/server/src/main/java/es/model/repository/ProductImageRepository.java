package es.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.model.domain.ProductImage;

public interface ProductImageRepository
    extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<ProductImage> {

  Optional<ProductImage> findById(Long pk);

  Page<ProductImage> findByIdIn(List<Long> pk, Pageable pageable);
  
  Page<ProductImage> findByProductId(Long pk, Pageable pageable);


}
