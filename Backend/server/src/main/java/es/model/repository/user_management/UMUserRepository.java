package es.model.repository.user_management;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import es.model.domain.user_management.UMUser;

/** Spring Data JPA repository for the User entity. */
public interface UMUserRepository
    extends JpaRepository<UMUser, Long>, JpaSpecificationExecutor<UMUser> {

  Optional<UMUser> findOneByResetKey(String resetKey);

  Optional<UMUser> findOneByEmail(String email);

  Optional<UMUser> findOneByLogin(String login);

  @Query(
      value = "select distinct user from UMUser user left join user.authorities",
      countQuery = "select count(user) from UMUser user")
  Page<UMUser> findAllWithAuthorities(Pageable pageable);
}
