package es.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.model.domain.Favourites;

public interface FavouritesRepository
    extends JpaRepository<Favourites, Long>, JpaSpecificationExecutor<Favourites> {

  Optional<Favourites> findById(Long pk);

  Page<Favourites> findByIdIn(List<Long> pk, Pageable pageable);

  Page<Favourites> findByAppuserId(String userId, Pageable pageable);

  void deleteFavouriteByAppuserIdAndProductId(String appuserId, Long productId);

}
