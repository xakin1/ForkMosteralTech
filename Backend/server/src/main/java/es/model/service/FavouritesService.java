package es.model.service;

import es.model.service.dto.FavouritesDTO;
import es.model.service.dto.FavouritesFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavouritesService {

  Page<FavouritesDTO> getAll(Pageable pageable, List<String> filters, String search);

  Page<FavouritesDTO> get(String userId, Pageable pageable) throws NotFoundException;

  FavouritesFullDTO create(FavouritesFullDTO favourites) throws OperationNotAllowedException;

  FavouritesFullDTO update(Long id, FavouritesFullDTO favourites)
      throws OperationNotAllowedException;

  void delete(Long id);

  void deleteFavouriteAppuserIdAndProductId(String appuserId, Long productId);
}
