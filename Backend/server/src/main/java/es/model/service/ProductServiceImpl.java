package es.model.service;

import es.model.domain.Product;
import es.model.repository.ProductRepository;
import es.model.service.dto.ProductDTO;
import es.model.service.dto.ProductFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.custom.FeatureCollectionJSON;
import es.web.rest.custom.FeatureJSON;
import es.web.rest.specifications.ProductSpecification;
import es.web.rest.util.specification_utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

  @Inject private ProductRepository productRepository;

  public Page<ProductDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<Product> page;
    if (search != null && !search.isEmpty()) {
      page = productRepository.findAll(ProductSpecification.searchAll(search), pageable);
    } else {
      page =
          productRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(ProductDTO::new);
  }

  public FeatureCollectionJSON getLocation(Boolean properties, List<String> filters) {
    List<Product> list =
        productRepository.findAll(SpecificationUtil.getSpecificationFromFilters(filters, false));

    List<FeatureJSON> ret =
        list.stream()
            .map(
                e -> {
                  FeatureJSON geoJSON = new FeatureJSON();
                  if (properties) {
                    geoJSON = new FeatureJSON(Product.class, e);
                  } else {
                    geoJSON.setProperties(new HashMap());
                  }
                  geoJSON.setId(e.getId());
                  geoJSON.getProperties().put("displayString", "" + e.getId() + "");
                  geoJSON.setGeometry(e.getLocation());
                  return geoJSON;
                })
            .filter(e -> e.getGeometry() != null)
            .collect(Collectors.toList());
    return new FeatureCollectionJSON(ret);
  }

  public ProductFullDTO get(Long id) throws NotFoundException {
    Product product = findById(id);
    return new ProductFullDTO(product);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public ProductFullDTO create(ProductFullDTO productDto) throws OperationNotAllowedException {
    if (productDto.getId() != null) {
      throw new OperationNotAllowedException("product.error.id-exists");
    }
    Product productEntity = productDto.toProduct();
    Product productSaved = productRepository.save(productEntity);
    return new ProductFullDTO(productSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public ProductFullDTO update(Long id, ProductFullDTO productDto)
      throws OperationNotAllowedException {
    if (productDto.getId() == null) {
      throw new OperationNotAllowedException("product.error.id-not-exists");
    }
    if (!id.equals(productDto.getId())) {
      throw new OperationNotAllowedException("product.error.id-dont-match");
    }
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new OperationNotAllowedException("product.error.id-not-exists"));
    Product productToUpdate = product;
    productToUpdate.setName(productDto.getName());
    productToUpdate.setDescription(productDto.getDescription());
    productToUpdate.setState(productDto.getState());
    if (productDto.getOwner() != null) {
      productToUpdate.setOwner(productDto.getOwner().toUser());
    } else {
      productToUpdate.setOwner(null);
    }
    Product productUpdated = productRepository.save(productToUpdate);
    return new ProductFullDTO(productUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    productRepository.deleteById(id);
  }

  /** PRIVATE METHODS * */
  private Product findById(Long id) throws NotFoundException {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find Product with id " + id));
  }
}
