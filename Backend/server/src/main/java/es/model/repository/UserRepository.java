package es.model.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.model.domain.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {

  Optional<AppUser> findById(String pk);
  
  Optional<AppUser> findByFirebaseToken(String token);

  Page<AppUser> findByIdIn(List<Long> pk, Pageable pageable);
}
