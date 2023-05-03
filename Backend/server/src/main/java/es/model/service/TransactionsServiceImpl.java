package es.model.service;

import es.model.domain.Transactions;
import es.model.repository.TransactionsRepository;
import es.model.service.dto.TransactionsDTO;
import es.model.service.dto.TransactionsFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.TransactionsSpecification;
import es.web.rest.util.specification_utils.*;

import java.util.List;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TransactionsServiceImpl implements TransactionsService {

  @Inject private TransactionsRepository transactionsRepository;

  public Page<TransactionsDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<Transactions> page;
    if (search != null && !search.isEmpty()) {
      page = transactionsRepository.findAll(TransactionsSpecification.searchAll(search), pageable);
    } else {
      page =
          transactionsRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(TransactionsDTO::new);
  }

  public TransactionsFullDTO get(Long id) throws NotFoundException {
    Transactions transactions = findById(id);
    return new TransactionsFullDTO(transactions);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public TransactionsFullDTO create(TransactionsFullDTO transactionsDto)
      throws OperationNotAllowedException {
    if (transactionsDto.getId() != null) {
      throw new OperationNotAllowedException("transactions.error.id-exists");
    }
    Transactions transactionsEntity = transactionsDto.toTransactions();
    Transactions transactionsSaved = transactionsRepository.save(transactionsEntity);
    return new TransactionsFullDTO(transactionsSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public TransactionsFullDTO update(Long id, TransactionsFullDTO transactionsDto)
      throws OperationNotAllowedException {
    if (transactionsDto.getId() == null) {
      throw new OperationNotAllowedException("transactions.error.id-not-exists");
    }
    if (!id.equals(transactionsDto.getId())) {
      throw new OperationNotAllowedException("transactions.error.id-dont-match");
    }
    Transactions transactions =
        transactionsRepository
            .findById(id)
            .orElseThrow(
                () -> new OperationNotAllowedException("transactions.error.id-not-exists"));
    Transactions transactionsToUpdate = transactionsDto.toTransactions();
    Transactions transactionsUpdated = transactionsRepository.save(transactionsToUpdate);
    return new TransactionsFullDTO(transactionsUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    transactionsRepository.deleteById(id);
  }

  /** PRIVATE METHODS * */
  private Transactions findById(Long id) throws NotFoundException {
    return transactionsRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find Transactions with id " + id));
  }
}
