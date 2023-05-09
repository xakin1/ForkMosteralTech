package es.model.service;

import es.model.service.dto.TransactionsDTO;
import es.model.service.dto.TransactionsFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionsService {

  Page<TransactionsDTO> getAll(Pageable pageable, List<String> filters, String search);

  TransactionsFullDTO get(Long id) throws NotFoundException;

  Page<TransactionsFullDTO> getTransactionsByProduct(Long id, int page, int size)
      throws NotFoundException;

  TransactionsFullDTO create(TransactionsFullDTO transactions)
      throws OperationNotAllowedException, NotFoundException;

  TransactionsFullDTO update(Long id, TransactionsFullDTO transactions)
      throws OperationNotAllowedException;

  void delete(Long id);

  Page<TransactionsFullDTO> getTransactionsBySeller(String userId, int page, int size)
      throws NotFoundException;

  Page<TransactionsFullDTO> getTransactionsByBuyer(String userId, int page, int size)
      throws NotFoundException;

  Long countTransactionsByBuyer(String userId);

  Long countTransactionsBySeller(String userId);

  Page<TransactionsFullDTO> getAllTransactions(String userId, int page, int size)
      throws NotFoundException;
}
