package es.model.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.model.domain.Furniture;
import es.model.domain.State;
import es.model.repository.FurnitureRepository;
import es.model.repository.FurnitureRepository.FurnitureProjection;
import es.model.service.dto.FurnitureDTO;
import es.model.service.dto.FurnitureFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.FurnitureSpecification;
import es.web.rest.util.specification_utils.SpecificationUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FurnitureServiceImpl implements FurnitureService {

	@Inject
	private FurnitureRepository furnitureRepository;

	public Page<FurnitureDTO> getAll(Pageable pageable, List<String> filters, String search) {
		Page<Furniture> page;
		if (search != null && !search.isEmpty()) {
			page = furnitureRepository.findAll(FurnitureSpecification.searchAll(search), pageable);
		} else {
			page = furnitureRepository.findAll(SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
		}
		return page.map(FurnitureDTO::new);
	}

	@Override
	public Page<FurnitureDTO> getAllFurnituresWithFavourites(String userId, Pageable pageable, Double minPrice,
			Double maxPrice, State state) throws NotFoundException {
		if (minPrice == null) {
			minPrice = 0.0;
		}

		if (maxPrice == null) {
			maxPrice = Double.MAX_VALUE;
		}

		Page<FurnitureProjection> products = findProductsWithFavourites(userId, pageable, minPrice, maxPrice, state);
		if (products.isEmpty()) {
			throw new NotFoundException("No se encontraron transacciones para el comprador con ID " + userId);
		}
		return products.map(FurnitureDTO::new);
	}

	public FurnitureFullDTO get(Long id) throws NotFoundException {
		Furniture furniture = findById(id);
		return new FurnitureFullDTO(furniture);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public FurnitureFullDTO create(FurnitureFullDTO furnitureDto) throws OperationNotAllowedException {
		if (furnitureDto.getId() != null) {
			throw new OperationNotAllowedException("furniture.error.id-exists");
		}
		Furniture furnitureEntity = furnitureDto.toFurniture();
		Furniture furnitureSaved = furnitureRepository.save(furnitureEntity);
		return new FurnitureFullDTO(furnitureSaved);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public FurnitureFullDTO update(Long id, FurnitureFullDTO furnitureDto) throws OperationNotAllowedException {
		if (furnitureDto.getId() == null) {
			throw new OperationNotAllowedException("furniture.error.id-not-exists");
		}
		if (!id.equals(furnitureDto.getId())) {
			throw new OperationNotAllowedException("furniture.error.id-dont-match");
		}
		Furniture furniture = furnitureRepository.findById(id)
				.orElseThrow(() -> new OperationNotAllowedException("furniture.error.id-not-exists"));
		Furniture furnitureToUpdate = furnitureDto.toFurniture();
		Furniture furnitureUpdated = furnitureRepository.save(furnitureToUpdate);
		return new FurnitureFullDTO(furnitureUpdated);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void delete(Long id) {
		furnitureRepository.deleteById(id);
	}

	/** PRIVATE METHODS * */
	private Furniture findById(Long id) throws NotFoundException {
		return furnitureRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cannot find Furniture with id " + id));
	}

	private Page<FurnitureProjection> findProductsWithFavourites(String userId, Pageable pageable, Double minPrice,
			Double maxPrice, State state) throws NotFoundException {
		return furnitureRepository.findFurnituresWithFavouritesByUserId(userId, minPrice, maxPrice, state, pageable);
	}

}
