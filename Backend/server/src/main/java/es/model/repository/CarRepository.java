package es.model.repository;

import es.model.domain.Car;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

  Optional<Car> findById(Long pk);

  Page<Car> findByIdIn(List<Long> pk, Pageable pageable);
}
