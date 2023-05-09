package es.web.rest;

import es.model.service.UserService;
import es.model.service.dto.UserDTO;
import es.model.service.dto.UserFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.FeatureCollectionJSON;
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
@RequestMapping(UserResource.USER_RESOURCE_URL)
public class UserResource {
  public static final String USER_RESOURCE_URL = "/api/entities/users";

  private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

  @Inject private UserService userService;

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
  @GetMapping("/all")
  public ResponseEntity<Page<UserDTO>> getAll(
      @PageableDefault(page = 0, size = 100000, sort = "id") Pageable pageable,
      @RequestParam(value = "filters", required = false) List<String> filters,
      @RequestParam(value = "search", required = false) String search) {
    Page<UserDTO> page = userService.getAll(pageable, filters, search);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, USER_RESOURCE_URL);
    return new ResponseEntity<>(page, headers, HttpStatus.OK);
  }

  @GetMapping("/geom/location")
  public ResponseEntity<FeatureCollectionJSON> getLocation(
      @RequestParam(value = "properties", defaultValue = "false", required = false)
          Boolean properties,
      @RequestParam(value = "filters", required = false) List<String> filters) {

    FeatureCollectionJSON featureCollection = userService.getLocation(properties, filters);
    return new ResponseEntity<>(featureCollection, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<UserFullDTO> get(@RequestParam String id) {
    try {
      return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/firebaseToken{firebase_token}")
  public ResponseEntity<UserFullDTO> getToken(@RequestParam String firebase_token) {
    try {
      return new ResponseEntity<>(userService.getFirebaseToken(firebase_token), HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody UserFullDTO user, Errors errors)
      throws URISyntaxException {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      UserFullDTO result = userService.create(user);
      return ResponseEntity.created(
              new URI(String.format("%s/%s", USER_RESOURCE_URL, result.getId())))
          .body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable String id, @Valid @RequestBody UserFullDTO user, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {
      if (user.getExpirationDatefirebaseToken() == null) {
        user.setExpirationDatefirebaseToken(null);
      }
      if (user.getLocation() == null) {
        user.setLocation(null);
      }
      UserFullDTO result = userService.update(id, user);
      return ResponseEntity.ok().body(result);
    } catch (OperationNotAllowedException e) {
      return ResponseEntity.badRequest()
          .headers(HeaderUtil.createError(e.getMessage(), null))
          .body(null);
    }
  }

  @PutMapping("/{id}/firebaseToken")
  public ResponseEntity<?> updateFirebaseToken(
      @PathVariable String id, @Valid @RequestBody String firebsaToken, Errors errors)
      throws NotFoundException {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorUtils.getValidationErrors(errors));
    }
    try {

      UserFullDTO user = userService.get(id);
      user.setFirebaseToken(firebsaToken);
      UserFullDTO result = userService.update(id, user);
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
      userService.delete(id);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .headers(HeaderUtil.createError("psql.exception", null))
          .build();
    }

    return ResponseEntity.ok().build();
  }
}
