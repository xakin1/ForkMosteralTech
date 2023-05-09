package es.model.service;

import es.model.service.dto.ProductDTO;
import es.model.service.dto.ProductFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.FeatureCollectionJSON;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Page<ProductDTO> getAll(Pageable pageable, List<String> filters, String search);

  FeatureCollectionJSON getLocation(Boolean properties, List<String> filters);

  ProductFullDTO get(Long id) throws NotFoundException;

  Page<ProductFullDTO> getByUserId(String userId, int page, int size) throws NotFoundException;
  
  Page<ProductDTO> getProductsWithFavourites (String userId, Pageable pageable) throws NotFoundException;

  ProductFullDTO create(ProductFullDTO product) throws OperationNotAllowedException;

  ProductFullDTO update(Long id, ProductFullDTO product) throws OperationNotAllowedException;

  void delete(Long id);
}
