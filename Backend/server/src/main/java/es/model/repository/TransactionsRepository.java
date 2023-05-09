package es.model.repository;

import es.model.domain.Transactions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository
    extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {

  Optional<Transactions> findById(Long pk);

  @Query("SELECT t FROM t_transactions t WHERE t.product.id = :productId")
  Page<Transactions> findByProductId(@Param("productId") Long productId, Pageable pageable);

  Page<Transactions> findByIdIn(List<Long> pk, Pageable pageable);

  @Query("SELECT t FROM t_transactions t WHERE t.seller.id = :userId")
  Page<Transactions> findBySellerId(@Param("userId") String userId, Pageable pageable);

  @Query("SELECT t FROM t_transactions t WHERE t.buyer.id = :userId")
  Page<Transactions> findByBuyerId(@Param("userId") String userId, Pageable pageable);

  @Query("SELECT COUNT(t) FROM t_transactions t WHERE t.seller.id = :userId")
  Long countBySellerId(@Param("userId") String userId);

  @Query("SELECT COUNT(t) FROM t_transactions t WHERE t.buyer.id = :userId")
  Long countByBuyerId(@Param("userId") String userId);

  @Query(
      "SELECT COUNT(t) FROM t_transactions t WHERE t.buyer.id = :userId OR t.seller.id = :userId")
  Page<Transactions> findById(String userId, Pageable pageable);
}
