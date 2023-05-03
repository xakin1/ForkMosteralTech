package es.model.service;

import es.model.service.dto.UserDTO;
import es.model.service.dto.UserFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.FeatureCollectionJSON;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<UserDTO> getAll(Pageable pageable, List<String> filters, String search);

  FeatureCollectionJSON getLocation(Boolean properties, List<String> filters);

  UserFullDTO get(Long id) throws NotFoundException;

  UserFullDTO create(UserFullDTO user) throws OperationNotAllowedException;

  UserFullDTO update(Long id, UserFullDTO user) throws OperationNotAllowedException;

  void delete(Long id);
}
