package es.model.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.model.domain.Car;
import es.model.domain.State;
import es.model.repository.CarRepository;
import es.model.repository.CarRepository.CarProjection;
import es.model.service.dto.CarDTO;
import es.model.service.dto.CarFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.CarSpecification;
import es.web.rest.util.specification_utils.SpecificationUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CarServiceImpl implements CarService {

	@Inject
	private CarRepository carRepository;

	public Page<CarDTO> getAll(Pageable pageable, List<String> filters, String search) {
		Page<Car> page;
		if (search != null && !search.isEmpty()) {
			page = carRepository.findAll(CarSpecification.searchAll(search), pageable);
		} else {
			page = carRepository.findAll(SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
		}
		return page.map(CarDTO::new);
	}

	
	@Override
	public Page<CarDTO> getAllCarWithFavourites(String userId, Pageable pageable, Double minPrice, Double maxPrice, Integer minKm, Integer maxKm, State state) throws NotFoundException {
		  
		  if (minPrice == null) {
		    minPrice = 0.0;
		  }
		  
		  if (maxPrice == null) {
		    maxPrice = Double.MAX_VALUE;
		  }
		  
		  if (minKm == null) {
		    minKm = 0;
		  }
		  
		  if (maxKm == null) {
			  maxKm = Integer.MAX_VALUE;
		  }
		  
		Page<CarProjection> products = findProductsWithFavourites(userId, pageable, minPrice, maxPrice, minKm, maxKm, state);

		if (products.isEmpty()) {
			throw new NotFoundException("No se encontraron transacciones para el comprador con ID " + userId);
		}
		return products.map(CarDTO::new);
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
		Car car = carRepository.findById(id)
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
		return carRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find Car with id " + id));
	}

	private Page<CarProjection> findProductsWithFavourites(String userId, Pageable pageable, Double minPrice, Double maxPrice, Integer minKm, Integer maxKm, State state) throws NotFoundException {
		return carRepository.findCarsWithFavouritesByUserId(userId, minPrice, maxPrice, minKm, maxKm, state, pageable);
	}
}
