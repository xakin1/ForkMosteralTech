package es.model.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.model.domain.ProductImage;
import es.model.repository.ProductImageRepository;
import es.model.service.dto.ProductImageDTO;
import es.model.service.dto.ProductImageFullDTO;
import es.model.service.exceptions.NotFoundException;
import es.model.service.exceptions.OperationNotAllowedException;
import es.web.rest.specifications.ProductImageSpecification;
import es.web.rest.util.specification_utils.SpecificationUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductImageServiceImpl implements ProductImageService {

  @Inject private ProductImageRepository productRepository;

  public Page<ProductImageDTO> getAll(Pageable pageable, List<String> filters, String search) {
    Page<ProductImage> page;
    if (search != null && !search.isEmpty()) {
      page = productRepository.findAll(ProductImageSpecification.searchAll(search), pageable);
    } else {
      page =
          productRepository.findAll(
              SpecificationUtil.getSpecificationFromFilters(filters, false), pageable);
    }
    return page.map(ProductImageDTO::new);
  }
    

  public ProductImageFullDTO get(Long id) throws NotFoundException {
    ProductImage product = findById(id);
    return new ProductImageFullDTO(product);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public ProductImageFullDTO create(ProductImageFullDTO productDto) throws OperationNotAllowedException {
    if (productDto.getId() != null) {
      throw new OperationNotAllowedException("product.error.id-exists");
    }
    ProductImage productEntity = productDto.toProduct();
    ProductImage productSaved = productRepository.save(productEntity);
    return new ProductImageFullDTO(productSaved);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public ProductImageFullDTO update(Long id, ProductImageFullDTO productImageDto)
      throws OperationNotAllowedException {
    if (productImageDto.getId() == null) {
      throw new OperationNotAllowedException("productImage.error.id-not-exists");
    }
    if (!id.equals(productImageDto.getId())) {
      throw new OperationNotAllowedException("productImage.error.id-dont-match");
    }
    if (productImageDto.getProduct() == null) {
        throw new OperationNotAllowedException("productImage.error.product-not-exists");
    }
    
    ProductImage product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new OperationNotAllowedException("productImage.error.id-not-exists"));
    ProductImage productImageToUpdate = product;
    productImageToUpdate.setName(productImageDto.getName());
    productImageToUpdate.setType(productImageDto.getType());
    productImageToUpdate.setSize(productImageDto.getSize());
    productImageToUpdate.setContent(productImageDto.getContent());
    productImageToUpdate.setProduct(productImageDto.getProduct());

    ProductImage productUpdated = productRepository.save(productImageToUpdate);
    return new ProductImageFullDTO(productUpdated);
  }

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(Long id) {
    productRepository.deleteById(id);
  }
  
  public Page<ProductImageDTO> getByProductId(Long id, Pageable page) throws NotFoundException {
	    Page<ProductImage> products = getByProduct(id, page);
	    if (products.isEmpty()) {
	      throw new NotFoundException(
	          "No se encontraron transacciones para el comprador con ID " + id);
	    }
	    return products.map(ProductImageDTO::new);
	  }

  /** PRIVATE METHODS * */
  private ProductImage findById(Long id) throws NotFoundException {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Cannot find ProductImage with id " + id));
  }


	private Page<ProductImage> getByProduct(Long id, Pageable page) throws NotFoundException {
	    return productRepository
	            .findByProductId(id, page);
	            
    }

}
