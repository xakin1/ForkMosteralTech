package es.model.service;

import es.model.domain.House;
import es.model.repository.HouseRepository;
import es.model.service.dto.HouseDTO;
import es.model.service.dto.HouseFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.HouseSpecification;
import es.web.rest.util.specification_utils.*;

import java.util.List;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class HouseServiceImpl implements HouseService {

  @Inject private HouseRepository houseRepository;

  public Page<HouseDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<House> page;
    if (search != null && !search.isEmpty()) {
      page = houseRepository.findAll(HouseSpecification.searchAll(search), pageable);
    } else {
      page =
          houseRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(HouseDTO::new);
  }

  public HouseFullDTO get(Long id) throws NotFoundException {
    House house = findById(id);
    return new HouseFullDTO(house);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public HouseFullDTO create(HouseFullDTO houseDto) throws OperationNotAllowedException {
    if (houseDto.getId() != null) {
      throw new OperationNotAllowedException("house.error.id-exists");
    }
    House houseEntity = houseDto.toHouse();
    House houseSaved = houseRepository.save(houseEntity);
    return new HouseFullDTO(houseSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public HouseFullDTO update(Long id, HouseFullDTO houseDto) throws OperationNotAllowedException {
    if (houseDto.getId() == null) {
      throw new OperationNotAllowedException("house.error.id-not-exists");
    }
    if (!id.equals(houseDto.getId())) {
      throw new OperationNotAllowedException("house.error.id-dont-match");
    }
    House house =
        houseRepository
            .findById(id)
            .orElseThrow(() -> new OperationNotAllowedException("house.error.id-not-exists"));
    House houseToUpdate = houseDto.toHouse();
    House houseUpdated = houseRepository.save(houseToUpdate);
    return new HouseFullDTO(houseUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    houseRepository.deleteById(id);
  }

  /** PRIVATE METHODS * */
  private House findById(Long id) throws NotFoundException {
    return houseRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find House with id " + id));
  }
}
