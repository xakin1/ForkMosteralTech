package es.web.rest;

import es.model.service.TransactionsService;
import es.model.service.dto.TransactionsDTO;
import es.model.service.dto.TransactionsFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.ValidationErrorUtils;
import es.web.rest.util.HeaderUtil;
import es.web.rest.util.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TransactionsResource.TRANSACTIONS_RESOURCE_URL)
public class TransactionsResource {
  public static final String TRANSACTIONS_RESOURCE_URL = "/api/entities/transactions";

  private static final Logger logger = LoggerFactory.getLogger(TransactionsResource.class);

  @Inject private TransactionsService transactionsService;

  /**
   * Get entities in pages<br>
   *
   * <p>If no <code>pageNum</code> is provided, then all results will be returned in one page
   *
   * @param pageable Contains all information about number page, items per page, order, ...
   * @param search Contains a text that will be searched in all the properties of the entity
   * @param filters Static filters to apply
   * @return Paginated entities
   */
  @GetMapping
  public ResponseEntity<Page<TransactionsDTO>> getAll(
      @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable,
      @RequestParam(value = "filters", required = false) List<String> filters,
      @RequestParam(value = "search", required = false) String search) {
    Page<TransactionsDTO> page = transactionsService.getAll(pageable, filters, search);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, TRANSACTIONS_RESOURCE_URL);
    return new ResponseEntity<>(page, headers, HttpStatus.OK);
  }

  @GetMapping("/sales")
  public ResponseEntity<Page<TransactionsFullDTO>> getAllSalesTransactions(
      @RequestParam String userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    try {
      Page<TransactionsFullDTO> transactions =
          transactionsService.getTransactionsBySeller(userId, page, size);
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/all/{userId}")
  public ResponseEntity<Page<TransactionsFullDTO>> getAllTransactions(
      @PathVariable String userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    try {
      Page<TransactionsFullDTO> transactions =
          transactionsService.getTransactionsBySeller(userId, page, size);
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/purchases")
  public ResponseEntity<Page<TransactionsFullDTO>> getAllPurchasesTransactions(
      @RequestParam String userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    try {
      Page<TransactionsFullDTO> transactions =
          transactionsService.getTransactionsByBuyer(userId, page, size);
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/sales/{userId}")
  public ResponseEntity<Long> countAllSalesTransactions(@PathVariable String userId) {
    Long transactions = transactionsService.countTransactionsBySeller(userId);
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }

  @GetMapping("/purchases/{userId}")
  public ResponseEntity<Long> countAllPurchasesTransactions(@PathVariable String userId) {
    Long transactions = transactionsService.countTransactionsByBuyer(userId);
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }

  @GetMapping("/product/{productId}/transactions")
  public ResponseEntity<Page<TransactionsFullDTO>> getAllProductTransactions(
      @PathVariable Long productId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    try {
      Page<TransactionsFullDTO> transactions =
          transactionsService.getTransactionsByProduct(productId, page, size);
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionsFullDTO> get(@PathVariable Long id) {
    try {
      return new ResponseEntity<>(transactionsService.get(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> create(
      @Valid @RequestBody TransactionsFullDTO transactions, Errors errors)
      throws URISyntaxException, NotFoundException {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      TransactionsFullDTO result = transactionsService.create(transactions);
      return ResponseEntity.created(
              new URI(String.format("%s/%s", TRANSACTIONS_RESOURCE_URL, result.getId())))
          .body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Long id, @Valid @RequestBody TransactionsFullDTO transactions, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      TransactionsFullDTO result = transactionsService.update(id, transactions);
      return ResponseEntity.ok().body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    try {
      transactionsService.delete(id);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(HeaderUtil.createError("psql.exception", null))
          .build();
    }

    return ResponseEntity.ok().build();
  }
}
