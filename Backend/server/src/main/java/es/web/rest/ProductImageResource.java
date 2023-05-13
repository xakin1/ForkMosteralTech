package es.web.rest;

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

import es.model.service.ProductImageService;
import es.model.service.dto.ProductImageDTO;
import es.model.service.dto.ProductImageFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.ValidationErrorUtils;
import es.web.rest.util.HeaderUtil;
import es.web.rest.util.PaginationUtil;

@RestController
@RequestMapping(ProductImageResource.PRODUCT_RESOURCE_URL)
public class ProductImageResource {
  public static final String PRODUCT_RESOURCE_URL = "/api/entities/productImages";

  private static final Logger logger = LoggerFactory.getLogger(ProductImageResource.class);

  @Inject private ProductImageService productImageService;

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
  public ResponseEntity<Page<ProductImageDTO>> getAll(
      @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable,
      @RequestParam(value = "filters", required = false) List<String> filters,
      @RequestParam(value = "search", required = false) String search) {
    Page<ProductImageDTO> page = productImageService.getAll(pageable, filters, search);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, PRODUCT_RESOURCE_URL);
    return new ResponseEntity<>(page, headers, HttpStatus.OK);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<ProductImageFullDTO> get(@PathVariable Long id) {
    try {
      return new ResponseEntity<>(productImageService.get(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("all/{product}")
  public ResponseEntity<Page<ProductImageDTO>> getByProductId(
      @PathVariable Long product, @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable) {
    try {
        Page<ProductImageDTO> page = productImageService.getByProductId(product, pageable);

      return new ResponseEntity<>(page, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody ProductImageFullDTO product, Errors errors)
      throws URISyntaxException {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      ProductImageFullDTO result = productImageService.create(product);
      return ResponseEntity.created(
              new URI(String.format("%s/%s", PRODUCT_RESOURCE_URL, result.getId())))
          .body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Long id, @Valid @RequestBody ProductImageFullDTO product, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      ProductImageFullDTO result = productImageService.update(id, product);
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
      productImageService.delete(id);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(HeaderUtil.createError("psql.exception", null))
          .build();
    }

    return ResponseEntity.ok().build();
  }
}
