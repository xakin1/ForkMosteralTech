package es.model.service;

import es.model.domain.Car;
import es.model.repository.CarRepository;
import es.model.service.dto.CarDTO;
import es.model.service.dto.CarFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.CarSpecification;
import es.web.rest.util.specification_utils.*;

import java.util.List;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CarServiceImpl implements CarService {

  @Inject private CarRepository carRepository;

  public Page<CarDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<Car> page;
    if (search != null && !search.isEmpty()) {
      page = carRepository.findAll(CarSpecification.searchAll(search), pageable);
    } else {
      page =
          carRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(CarDTO::new);
  }

  public CarFullDTO get(Long id) throws NotFoundException {
    Car car = findById(id);
    return new CarFullDTO(car);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public CarFullDTO create(CarFullDTO carDto) throws OperationNotAllowedException {
    if (carDto.getId() != null) {
      throw new OperationNotAllowedException("car.error.id-exists");
    }
    Car carEntity = carDto.toCar();
    Car carSaved = carRepository.save(carEntity);
    return new CarFullDTO(carSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public CarFullDTO update(Long id, CarFullDTO carDto) throws OperationNotAllowedException {
    if (carDto.getId() == null) {
      throw new OperationNotAllowedException("car.error.id-not-exists");
    }
    if (!id.equals(carDto.getId())) {
      throw new OperationNotAllowedException("car.error.id-dont-match");
    }
    Car car =
        carRepository
            .findById(id)
            .orElseThrow(() -> new OperationNotAllowedException("car.error.id-not-exists"));
    Car carToUpdate = carDto.toCar();
    Car carUpdated = carRepository.save(carToUpdate);
    return new CarFullDTO(carUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    carRepository.deleteById(id);
  }

  /** PRIVATE METHODS * */
  private Car findById(Long id) throws NotFoundException {
    return carRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find Car with id " + id));
  }
}
