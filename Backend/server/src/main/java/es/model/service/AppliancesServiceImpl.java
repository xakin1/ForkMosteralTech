package es.model.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.model.domain.Appliances;
import es.model.domain.State;
import es.model.repository.AppliancesRepository;
import es.model.repository.AppliancesRepository.AppliancesProjection;
import es.model.service.dto.AppliancesDTO;
import es.model.service.dto.AppliancesFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.AppliancesSpecification;
import es.web.rest.util.specification_utils.SpecificationUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppliancesServiceImpl implements AppliancesService {

	@Inject
	private AppliancesRepository appliancesRepository;

	public Page<AppliancesDTO> getAll(Pageable pageable, List<String> filters, String search) {
		Page<Appliances> page;
		if (search != null && !search.isEmpty()) {
			page = appliancesRepository.findAll(AppliancesSpecification.searchAll(search), pageable);
		} else {
			page = appliancesRepository.findAll(SpecificationUtil.getSpecificationFromFilters(filters, false),
					pageable);
		}
		return page.map(AppliancesDTO::new);
	}

	@Override
	public Page<AppliancesDTO> getAllAppliancesWithFavourites(String userId, Pageable pageable, Double minPrice,
			Double maxPrice, State state) throws NotFoundException {
		Page<AppliancesProjection> products = findProductsWithFavourites(userId, pageable, minPrice, maxPrice, state);
		if (products.isEmpty()) {
			throw new NotFoundException("No se encontraron transacciones para el comprador con ID " + userId);
		}
		return products.map(AppliancesDTO::new);
	}

	public AppliancesFullDTO get(Long id) throws NotFoundException {
		Appliances appliances = findById(id);
		return new AppliancesFullDTO(appliances);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public AppliancesFullDTO create(AppliancesFullDTO appliancesDto) throws OperationNotAllowedException {
		if (appliancesDto.getId() != null) {
			throw new OperationNotAllowedException("appliances.error.id-exists");
		}
		Appliances appliancesEntity = appliancesDto.toAppliances();
		Appliances appliancesSaved = appliancesRepository.save(appliancesEntity);
		return new AppliancesFullDTO(appliancesSaved);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public AppliancesFullDTO update(Long id, AppliancesFullDTO appliancesDto) throws OperationNotAllowedException {
		if (appliancesDto.getId() == null) {
			throw new OperationNotAllowedException("appliances.error.id-not-exists");
		}
		if (!id.equals(appliancesDto.getId())) {
			throw new OperationNotAllowedException("appliances.error.id-dont-match");
		}
		Appliances appliances = appliancesRepository.findById(id)
				.orElseThrow(() -> new OperationNotAllowedException("appliances.error.id-not-exists"));
		Appliances appliancesToUpdate = appliancesDto.toAppliances();
		Appliances appliancesUpdated = appliancesRepository.save(appliancesToUpdate);
		return new AppliancesFullDTO(appliancesUpdated);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void delete(Long id) {
		appliancesRepository.deleteById(id);
	}

	/** PRIVATE METHODS * */
	private Appliances findById(Long id) throws NotFoundException {
		return appliancesRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cannot find Appliances with id " + id));
	}

	private Page<AppliancesProjection> findProductsWithFavourites(String userId, Pageable pageable, Double minPrice,
			Double maxPrice, State state) throws NotFoundException {
		return appliancesRepository.findAppliancesWithFavouritesByUserId(userId, minPrice, maxPrice, state, pageable);
	}
}
