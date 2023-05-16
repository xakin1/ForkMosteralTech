package es.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.model.domain.State;
import es.model.service.dto.HouseDTO;
import es.model.service.dto.HouseFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;

public interface HouseService {

  Page<HouseDTO> getAll(Pageable pageable, List<String> filters, String search);
  
  Page<HouseDTO> getAllHousesWithFavourites(String userId, Pageable pageable, Double minPrice, Double maxPrice, Integer minm2, Integer maxm2, State state) throws NotFoundException;

  HouseFullDTO get(Long id) throws NotFoundException;

  HouseFullDTO create(HouseFullDTO house) throws OperationNotAllowedException;

  HouseFullDTO update(Long id, HouseFullDTO house) throws OperationNotAllowedException;

  void delete(Long id);
}
