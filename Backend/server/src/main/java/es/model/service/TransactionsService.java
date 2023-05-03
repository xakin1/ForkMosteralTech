package es.model.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.model.service.dto.TransactionsDTO;
import es.model.service.dto.TransactionsFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;

public interface TransactionsService {

  Page<TransactionsDTO> getAll(Pageable pageable, List<String> filters, String search);

  TransactionsFullDTO get(Long id) throws NotFoundException;

  TransactionsFullDTO create(TransactionsFullDTO transactions) throws OperationNotAllowedException;

  TransactionsFullDTO update(Long id, TransactionsFullDTO transactions)
      throws OperationNotAllowedException;

  void delete(Long id);
}
