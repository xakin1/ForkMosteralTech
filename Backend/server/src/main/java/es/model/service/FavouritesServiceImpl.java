package es.model.service;

import es.model.domain.Favourites;
import es.model.repository.FavouritesRepository;
import es.model.service.dto.FavouritesDTO;
import es.model.service.dto.FavouritesFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.FavouritesSpecification;
import es.web.rest.util.specification_utils.*;

import java.util.List;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FavouritesServiceImpl implements FavouritesService {

  @Inject private FavouritesRepository favouritesRepository;

  public Page<FavouritesDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<Favourites> page;
    if (search != null && !search.isEmpty()) {
      page = favouritesRepository.findAll(FavouritesSpecification.searchAll(search), pageable);
    } else {
      page =
          favouritesRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(FavouritesDTO::new);
  }

  public FavouritesFullDTO get(Long id) throws NotFoundException {
    Favourites favourites = findById(id);
    return new FavouritesFullDTO(favourites);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public FavouritesFullDTO create(FavouritesFullDTO favouritesDto)
      throws OperationNotAllowedException {
    if (favouritesDto.getId() != null) {
      throw new OperationNotAllowedException("favourites.error.id-exists");
    }
    Favourites favouritesEntity = favouritesDto.toFavourites();
    Favourites favouritesSaved = favouritesRepository.save(favouritesEntity);
    return new FavouritesFullDTO(favouritesSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public FavouritesFullDTO update(Long id, FavouritesFullDTO favouritesDto)
      throws OperationNotAllowedException {
    if (favouritesDto.getId() == null) {
      throw new OperationNotAllowedException("favourites.error.id-not-exists");
    }
    if (!id.equals(favouritesDto.getId())) {
      throw new OperationNotAllowedException("favourites.error.id-dont-match");
    }
    Favourites favourites =
        favouritesRepository
            .findById(id)
            .orElseThrow(() -> new OperationNotAllowedException("favourites.error.id-not-exists"));
    Favourites favouritesToUpdate = favouritesDto.toFavourites();
    Favourites favouritesUpdated = favouritesRepository.save(favouritesToUpdate);
    return new FavouritesFullDTO(favouritesUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    favouritesRepository.deleteById(id);
  }

  /** PRIVATE METHODS * */
  private Favourites findById(Long id) throws NotFoundException {
    return favouritesRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find Favourites with id " + id));
  }
}
