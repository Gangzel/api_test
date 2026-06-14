package com.filmSociety.movies_api.actor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorsRepository extends JpaRepository<ActorEntity, Long> {

  Page<ActorEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

  Page<ActorEntity> findByMovies_Id(Long movieId, Pageable pageable);
}