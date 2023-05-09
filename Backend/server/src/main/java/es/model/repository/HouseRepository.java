package es.model.repository;

import es.model.domain.House;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseRepository
    extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

  Optional<House> findById(Long pk);

  Page<House> findByIdIn(List<Long> pk, Pageable pageable);
}
