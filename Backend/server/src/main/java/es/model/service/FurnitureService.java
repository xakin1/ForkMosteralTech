package es.model.service;

import es.model.domain.State;
import es.model.service.dto.FurnitureDTO;
import es.model.service.dto.FurnitureFullDTO;
import es.model.service.dto.HouseDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FurnitureService {

  Page<FurnitureDTO> getAll(Pageable pageable, List<String> filters, String search);
  
  Page<FurnitureDTO> getAllFurnituresWithFavourites(String userId, Pageable pageable, Double minPrice, Double maxPrice, State state) throws NotFoundException;

  FurnitureFullDTO get(Long id) throws NotFoundException;

  FurnitureFullDTO create(FurnitureFullDTO furniture) throws OperationNotAllowedException;

  FurnitureFullDTO update(Long id, FurnitureFullDTO furniture) throws OperationNotAllowedException;

  void delete(Long id);
}
