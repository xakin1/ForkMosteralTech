package es.model.repository.user_management;

import org.springframework.data.jpa.repository.JpaRepository;

import es.model.domain.user_management.Authority;

/** Spring Data JPA repository for the Authority entity. */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
