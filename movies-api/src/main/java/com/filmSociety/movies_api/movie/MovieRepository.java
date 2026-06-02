package com.filmSociety.movies_api.movie;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovieRepository extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {

  // Search by title (Case insensetive)
  Page<MovieEntity> findByTitleContainingIgnoreCase(
      String name,
      Pageable pageable);

  // Check if movie contains genre
  long countByGenres_Id(Long genreId);

  // Check if movie contains actor
  long countByActors_Id(Long actorId);

  // Find all movies of where certain actor (by id) exists
  Page<MovieEntity> findByActors_Id(Long actorId, Pageable pageable);

  // Find all movies with certain genre (id)
  Page<MovieEntity> findByGenres_Id(Long genreId, Pageable pageable);
}
