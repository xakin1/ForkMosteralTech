package es.model.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.model.domain.Transactions;

public interface TransactionsRepository
    extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {

  Optional<Transactions> findById(Long pk);

  Page<Transactions> findByIdIn(List<Long> pk, Pageable pageable);
}
