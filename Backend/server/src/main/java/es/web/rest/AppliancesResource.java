package es.web.rest;

import es.model.domain.State;
import es.model.service.AppliancesService;
import es.model.service.dto.AppliancesDTO;
import es.model.service.dto.AppliancesFullDTO;
import es.model.service.dto.CarDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.ValidationErrorUtils;
import es.web.rest.util.HeaderUtil;
import es.web.rest.util.PaginationUtil;
import es.web.rest.util.specification_utils.*;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppliancesResource.APPLIANCES_RESOURCE_URL)
public class AppliancesResource {
  public static final String APPLIANCES_RESOURCE_URL = "/api/entities/appliances";

  private static final Logger logger = LoggerFactory.getLogger(AppliancesResource.class);

  @Inject private AppliancesService appliancesService;

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
  public ResponseEntity<Page<AppliancesDTO>> getAll(
      @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable,
      @RequestParam(value = "filters", required = false) List<String> filters,
      @RequestParam(value = "search", required = false) String search) {
    Page<AppliancesDTO> page = appliancesService.getAll(pageable, filters, search);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, APPLIANCES_RESOURCE_URL);
    return new ResponseEntity<>(page, headers, HttpStatus.OK);
  }
  
  @GetMapping("all/favourites/{userId}")
  public ResponseEntity<Page<AppliancesDTO>> getProductsWithFavourites(
		  @PathVariable String userId,
	      @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable,
	      @RequestParam(value = "minPrice", required = false) Double minPrice,
	      @RequestParam(value = "maxPrice", required = false) Double maxPrice,
	      @RequestParam(value = "state", required = false) State state) throws NotFoundException {
    Page<AppliancesDTO> page = appliancesService.getAllAppliancesWithFavourites(userId, pageable, minPrice, maxPrice, state);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, APPLIANCES_RESOURCE_URL);
    return new ResponseEntity<>(page, headers, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AppliancesFullDTO> get(@PathVariable Long id) {
    try {
      return new ResponseEntity<>(appliancesService.get(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody AppliancesFullDTO appliances, Errors errors)
      throws URISyntaxException {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      AppliancesFullDTO result = appliancesService.create(appliances);
      return ResponseEntity.created(
              new URI(String.format("%s/%s", APPLIANCES_RESOURCE_URL, result.getId())))
          .body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Long id, @Valid @RequestBody AppliancesFullDTO appliances, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      AppliancesFullDTO result = appliancesService.update(id, appliances);
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
      appliancesService.delete(id);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(HeaderUtil.createError("psql.exception", null))
          .build();
    }

    return ResponseEntity.ok().build();
  }
}
