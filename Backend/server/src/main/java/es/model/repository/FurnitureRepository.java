package es.model.repository;

import es.model.domain.Furniture;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FurnitureRepository
    extends JpaRepository<Furniture, Long>, JpaSpecificationExecutor<Furniture> {

  Optional<Furniture> findById(Long pk);

  Page<Furniture> findByIdIn(List<Long> pk, Pageable pageable);
}
