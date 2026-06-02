package com.filmSociety.movies_api.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<GenreEntity, Long> {
}
