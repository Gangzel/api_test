package com.filmSociety.movies_api.genre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.filmSociety.movies_api.actor.ActorEntity;
import com.filmSociety.movies_api.genre.dto.CreateGenreRequest;
import com.filmSociety.movies_api.genre.dto.GenreSummaryResponse;
import com.filmSociety.movies_api.movie.MovieRepository;
import com.filmSociety.movies_api.movie.dto.MovieSummaryResponse;

@Service
public class GenresService {

  private final GenresRepository genresRepository;
  private final MovieRepository movieRepository;

  public GenresService(GenresRepository genresRepository, MovieRepository movieRepository) {
    this.genresRepository = genresRepository;
    this.movieRepository = movieRepository;
  }

  public Page<GenreSummaryResponse> getAllGenreEntities(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return genresRepository.findAll(pageable).map(this::toResponse);
  }

  public GenreSummaryResponse getEntityById(Long id) {
    return toResponse(findGenreOrThrow(id));
  }

  public GenreSummaryResponse updateGenre(Long id, GenreEntity update) {
    GenreEntity genre = findGenreOrThrow(id);
    genre.setName(update.getName());
    return toResponse(genresRepository.save(genre));
  }

  public GenreSummaryResponse createGenreEntity(CreateGenreRequest request) {
    GenreEntity genre = new GenreEntity();
    genre.setName(request.getName());
    return toResponse(genresRepository.save(genre));
  }

  public void deleteGenreEntity(Long id, Boolean force) {
    GenreEntity genre = findGenreOrThrow(id);

    if (force) {
      genresRepository.delete(genre);
    } else {
      long moviesCount = movieRepository.countByGenres_Id(id);
      if (moviesCount > 0) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Genre is used by " + moviesCount + " movies");
      }
      genresRepository.delete(genre);
    }
  }

  public Page<MovieSummaryResponse> getMoviesOfGenre(Long id, Integer page, Integer size) {
    findGenreOrThrow(id);
    Pageable pageable = PageRequest.of(page, size);
    return movieRepository.findByGenres_Id(id, pageable).map(movie -> new MovieSummaryResponse(
        movie.getTitle(), movie.getId(), movie.getReleaseYear(), movie.getDuration(),
        movie.getActors().stream().map(ActorEntity::getId).toList(),
        movie.getGenres().stream().map(GenreEntity::getId).toList()));
  }

  // --- Helpers ---

  private GenreEntity findGenreOrThrow(Long id) {
    return genresRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
  }

  private GenreSummaryResponse toResponse(GenreEntity genre) {
    return new GenreSummaryResponse(genre.getId(), genre.getName());
  }
}
