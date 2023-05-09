package es.model.repository.user_management;

import es.model.domain.user_management.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository for the Authority entity. */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
