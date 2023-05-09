package es.model.service;

import es.model.domain.Transactions;
import es.model.repository.TransactionsRepository;
import es.model.service.dto.ProductFullDTO;
import es.model.service.dto.TransactionsDTO;
import es.model.service.dto.TransactionsFullDTO;
import es.model.service.dto.UserDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.TransactionsSpecification;
import es.web.rest.util.specification_utils.SpecificationUtil;
import java.util.List;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TransactionsServiceImpl implements TransactionsService {

  @Inject private TransactionsRepository transactionsRepository;
  @Inject private ProductService productService;

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

  public Page<TransactionsFullDTO> getTransactionsByProduct(Long productId, int page, int size)
      throws NotFoundException {
    Pageable pageable = PageRequest.of(page, size);
    Page<Transactions> transactions = findByProduct(productId, pageable);
    if (transactions.isEmpty()) {
      throw new NotFoundException(
          "No se encontraron transacciones para el producto con ID " + productId);
    }
    return transactions.map(TransactionsFullDTO::new);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public TransactionsFullDTO create(TransactionsFullDTO transactionsDto)
      throws OperationNotAllowedException, NotFoundException {
    if (transactionsDto.getId() != null) {
      throw new OperationNotAllowedException("transactions.error.id-exists");
    }
    Long productId = transactionsDto.getProduct().getId();
    UserDTO buyer = transactionsDto.getBuyer();
    ProductFullDTO product = productService.get(productId);
    product.setOwner(buyer);
    productService.update(productId, product);
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

  @Override
  public Page<TransactionsFullDTO> getTransactionsBySeller(String userId, int page, int size)
      throws NotFoundException {
    Pageable pageable = PageRequest.of(page, size);
    Page<Transactions> transactions = findBySeller(userId, pageable);
    if (transactions.isEmpty()) {
      throw new NotFoundException(
          "No se encontraron transacciones para el vendedor con ID " + userId);
    }
    return transactions.map(TransactionsFullDTO::new);
  }

  @Override
  public Page<TransactionsFullDTO> getAllTransactions(String userId, int page, int size)
      throws NotFoundException {
    Pageable pageable = PageRequest.of(page, size);
    Page<Transactions> transactions = findBySeller(userId, pageable);
    if (transactions.isEmpty()) {
      throw new NotFoundException(
          "No se encontraron transacciones para el vendedor con ID " + userId);
    }
    return transactions.map(TransactionsFullDTO::new);
  }

  @Override
  public Page<TransactionsFullDTO> getTransactionsByBuyer(String userId, int page, int size)
      throws NotFoundException {
    Pageable pageable = PageRequest.of(page, size);
    Page<Transactions> transactions = findByBuyer(userId, pageable);
    if (transactions.isEmpty()) {
      throw new NotFoundException(
          "No se encontraron transacciones para el comprador con ID " + userId);
    }
    return transactions.map(TransactionsFullDTO::new);
  }

  @Override
  public Long countTransactionsByBuyer(String userId) {
    return countByBuyer(userId);
  }

  @Override
  public Long countTransactionsBySeller(String userId) {
    return countBySeller(userId);
  }

  /** PRIVATE METHODS * */
  private Transactions findById(Long id) throws NotFoundException {
    return transactionsRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find Transactions with id " + id));
  }

  private Page<Transactions> findByProduct(Long productId, Pageable pageable)
      throws NotFoundException {
    return transactionsRepository.findByProductId(productId, pageable);
  }

  private Page<Transactions> findBySeller(String userId, Pageable pageable)
      throws NotFoundException {
    return transactionsRepository.findBySellerId(userId, pageable);
  }

  private Page<Transactions> findById(String userId, Pageable pageable) throws NotFoundException {
    return transactionsRepository.findById(userId, pageable);
  }

  private Page<Transactions> findByBuyer(String userId, Pageable pageable)
      throws NotFoundException {
    return transactionsRepository.findByBuyerId(userId, pageable);
  }

  private Long countBySeller(String userId) {
    return transactionsRepository.countBySellerId(userId);
  }

  private Long countByBuyer(String userId) {
    return transactionsRepository.countByBuyerId(userId);
  }
}
