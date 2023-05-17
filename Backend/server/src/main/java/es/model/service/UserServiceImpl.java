package es.model.service;

import es.model.domain.AppUser;
import es.model.repository.UserRepository;
import es.model.service.dto.UserDTO;
import es.model.service.dto.UserFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.FeatureCollectionJSON;
import es.web.rest.custom.FeatureJSON;
import es.web.rest.specifications.UserSpecification;
import es.web.rest.util.specification_utils.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

  @Inject private UserRepository userRepository;

  public Page<UserDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<AppUser> page;
    if (search != null && !search.isEmpty()) {
      page = userRepository.findAll(UserSpecification.searchAll(search), pageable);
    } else {
      page =
          userRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(UserDTO::new);
  }

  public FeatureCollectionJSON getLocation(Boolean properties, List<String> filters) {
    List<AppUser> list =
        userRepository.findAll(SpecificationUtil.getSpecificationFromFilters(filters, false));

    List<FeatureJSON> ret =
        list.stream()
            .map(
                e -> {
                  FeatureJSON geoJSON = new FeatureJSON();
                  if (properties) {
                    geoJSON = new FeatureJSON(AppUser.class, e);
                  } else {
                    geoJSON.setProperties(new HashMap());
                  }
                  //                  geoJSON.setId(e.getId());
                  //                  geoJSON.getProperties().put("displayString", "" + e.getId() +
                  // "");
                  return geoJSON;
                })
            .collect(Collectors.toList());
    return new FeatureCollectionJSON(ret);
  }

  public UserFullDTO get(String id) throws NotFoundException {
    AppUser user = findById(id);
    return new UserFullDTO(user);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public UserFullDTO create(UserFullDTO userDto) throws OperationNotAllowedException {
    AppUser userEntity = userDto.toUser();
    AppUser userSaved = userRepository.save(userEntity);
    return new UserFullDTO(userSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public UserFullDTO update(String id, UserFullDTO userDto) throws OperationNotAllowedException {
    if (userDto.getId() == null) {
      throw new OperationNotAllowedException("user.error.id-not-exists");
    }
    if (!id.equals(userDto.getId())) {
      throw new OperationNotAllowedException("user.error.id-dont-match");
    }
    AppUser user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new OperationNotAllowedException("user.error.id-not-exists"));
    AppUser userToUpdate = userDto.toUser();
    AppUser userUpdated = userRepository.save(userToUpdate);
    return new UserFullDTO(userUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    userRepository.deleteById(id);
  }

  /** PRIVATE METHODS * */
  private AppUser findById(String id) throws NotFoundException {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find User with id " + id));
  }

  @Override
  public UserFullDTO getFirebaseToken(String token) throws NotFoundException {
    AppUser user = findByFirebaseToken(token);
    return new UserFullDTO(user);
  }

  private AppUser findByFirebaseToken(String token) throws NotFoundException {
    return userRepository
        .findByFirebaseToken(token)
        .orElseThrow(() -> new NotFoundException("Cannot find User with id " + token));
  }
}
