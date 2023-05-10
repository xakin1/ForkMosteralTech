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
import org.springframework.data.repository.query.Param;
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

import es.model.service.FavouritesService;
import es.model.service.dto.FavouritesDTO;
import es.model.service.dto.FavouritesFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.ValidationErrorUtils;
import es.web.rest.util.HeaderUtil;
import es.web.rest.util.PaginationUtil;

@RestController
@RequestMapping(FavouritesResource.FAVOURITES_RESOURCE_URL)
public class FavouritesResource {
  public static final String FAVOURITES_RESOURCE_URL = "/api/entities/favourites";

  private static final Logger logger = LoggerFactory.getLogger(FavouritesResource.class);

  @Inject private FavouritesService favouritesService;

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
  public ResponseEntity<Page<FavouritesDTO>> getAll(
      @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable,
      @RequestParam(value = "filters", required = false) List<String> filters,
      @RequestParam(value = "search", required = false) String search) {
    Page<FavouritesDTO> page = favouritesService.getAll(pageable, filters, search);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, FAVOURITES_RESOURCE_URL);
    return new ResponseEntity<>(page, headers, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<Page<FavouritesDTO>> get(@PathVariable String userId,@PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable) {
    try {
    	Page<FavouritesDTO> page = favouritesService.get(userId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, FAVOURITES_RESOURCE_URL);
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody FavouritesFullDTO favourites, Errors errors)
      throws URISyntaxException {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      FavouritesFullDTO result = favouritesService.create(favourites);
      return ResponseEntity.created(
              new URI(String.format("%s/%s", FAVOURITES_RESOURCE_URL, result.getId())))
          .body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Long id, @Valid @RequestBody FavouritesFullDTO favourites, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      FavouritesFullDTO result = favouritesService.update(id, favourites);
      return ResponseEntity.ok().body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @DeleteMapping("/{appuserId}")
  public ResponseEntity<Void> delete(@PathVariable String appuserId, @RequestParam Long productId) {
    try {
      favouritesService.deleteFavouriteAppuserIdAndProductId(appuserId,productId);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(HeaderUtil.createError("psql.exception", null))
          .build();
    }

    return ResponseEntity.ok().build();
  }
}
