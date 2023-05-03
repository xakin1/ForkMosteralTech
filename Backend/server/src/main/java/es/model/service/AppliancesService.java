package es.model.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.model.service.dto.AppliancesDTO;
import es.model.service.dto.AppliancesFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;

public interface AppliancesService {

  Page<AppliancesDTO> getAll(Pageable pageable, List<String> filters, String search);

  AppliancesFullDTO get(Long id) throws NotFoundException;

  AppliancesFullDTO create(AppliancesFullDTO appliances) throws OperationNotAllowedException;

  AppliancesFullDTO update(Long id, AppliancesFullDTO appliances)
      throws OperationNotAllowedException;

  void delete(Long id);
}
