package es.model.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.model.domain.Appliances;

public interface AppliancesRepository
    extends JpaRepository<Appliances, Long>, JpaSpecificationExecutor<Appliances> {

  Optional<Appliances> findById(Long pk);

  Page<Appliances> findByIdIn(List<Long> pk, Pageable pageable);
}
