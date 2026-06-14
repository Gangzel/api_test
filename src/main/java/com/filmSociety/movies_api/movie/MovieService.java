package com.filmSociety.movies_api.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import com.filmSociety.movies_api.actor.ActorEntity;
import com.filmSociety.movies_api.actor.ActorsRepository;
import com.filmSociety.movies_api.actor.dto.ActorSummaryResponse;
import com.filmSociety.movies_api.genre.GenreEntity;
import com.filmSociety.movies_api.genre.GenresRepository;
import com.filmSociety.movies_api.movie.dto.CreateMovieRequest;
import com.filmSociety.movies_api.movie.dto.MovieSummaryResponse;
import com.filmSociety.movies_api.movie.dto.UpdateMovieRequest;
 
@Service
public class MovieService {
 
  private final MovieRepository movieRepository;
  private final ActorsRepository actorsRepository;
  private final GenresRepository genresRepository;
 
  public MovieService(MovieRepository movieRepository, ActorsRepository actorsRepository,
      GenresRepository genresRepository) {
    this.movieRepository = movieRepository;
    this.actorsRepository = actorsRepository;
    this.genresRepository = genresRepository;
  }
 
  public Page<MovieSummaryResponse> getAllMovies(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return movieRepository.findAll(pageable).map(this::toResponse);
  }
 
  public Page<MovieSummaryResponse> searchMoviesByTitle(String title, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return movieRepository.findByTitleContainingIgnoreCase(title, pageable).map(this::toResponse);
  }
 
  public Page<MovieSummaryResponse> filterByParams(Long genre, Integer year, Long actor, Integer page, Integer size) {
    Specification<MovieEntity> spec = (root, query, cb) -> cb.conjunction();
 
    if (genre != null) {
      spec = spec.and((root, query, cb) -> {
        query.distinct(true);
        return cb.equal(root.join("genres").get("id"), genre);
      });
    }
    if (year != null) {
      spec = spec.and((root, query, cb) -> cb.equal(root.get("releaseYear"), year));
    }
    if (actor != null) {
      spec = spec.and((root, query, cb) -> {
        query.distinct(true);
        return cb.equal(root.join("actors").get("id"), actor);
      });
    }
 
    Pageable pageable = PageRequest.of(page, size);
    return movieRepository.findAll(spec, pageable).map(this::toResponse);
  }
 
  public MovieSummaryResponse getMovieById(Long id) {
    return toResponse(findMovieOrThrow(id));
  }
 
  public MovieSummaryResponse updateMovieDetails(Long id, UpdateMovieRequest update) {
    MovieEntity movie = findMovieOrThrow(id);
 
    if (update.getTitle() != null) movie.setTitle(update.getTitle());
    if (update.getDuration() != null) movie.setDuration(update.getDuration());
    if (update.getReleaseYear() != null) movie.setReleaseYear(update.getReleaseYear());
 
    return toResponse(movieRepository.save(movie));
  }
 
  public MovieSummaryResponse createNewMovie(CreateMovieRequest request) {
    MovieEntity movie = new MovieEntity();
    movie.setTitle(request.getTitle());
    movie.setDuration(request.getDuration());
    movie.setReleaseYear(request.getReleaseYear());
    return toResponse(movieRepository.save(movie));
  }
 
  public void deleteMovieById(Long id) {
    movieRepository.delete(findMovieOrThrow(id));
  }
 
  public Page<ActorSummaryResponse> getAllActorsOfMovieById(Long id, Integer page, Integer size) {
    findMovieOrThrow(id); // ensures 404 if movie doesn't exist
    Pageable pageable = PageRequest.of(page, size);
    return actorsRepository.findByMovies_Id(id, pageable)
        .map(actor -> new ActorSummaryResponse(
            actor.getId(), actor.getName(),
            actor.getMovies().stream().map(MovieEntity::getId).toList(),
            actor.getBirthDate()));
  }
 
  public MovieSummaryResponse addActorToMovie(Long id, Long actorId) {
    MovieEntity movie = findMovieOrThrow(id);
    ActorEntity actor = actorsRepository.findById(actorId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));
 
    movie.getActors().add(actor);
    return toResponse(movieRepository.save(movie));
  }
 
  public void removeActorFromMovie(Long id, Long actorId) {
    MovieEntity movie = findMovieOrThrow(id);
    actorsRepository.findById(actorId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));
 
    movie.getActors().removeIf(a -> a.getId().equals(actorId));
    movieRepository.save(movie);
  }
 
  public MovieSummaryResponse addGenreToMovie(Long id, Long genreId) {
    MovieEntity movie = findMovieOrThrow(id);
    GenreEntity genre = genresRepository.findById(genreId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
 
    movie.getGenres().add(genre);
    return toResponse(movieRepository.save(movie));
  }
 
  public void removeGenreFromMovie(Long id, Long genreId) {
    MovieEntity movie = findMovieOrThrow(id);
    genresRepository.findById(genreId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
 
    movie.getGenres().removeIf(g -> g.getId().equals(genreId));
    movieRepository.save(movie);
  }
 
  // --- Helpers ---
 
  private MovieEntity findMovieOrThrow(Long id) {
    return movieRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
  }
 
  private MovieSummaryResponse toResponse(MovieEntity movie) {
    return new MovieSummaryResponse(
        movie.getTitle(),
        movie.getId(),
        movie.getReleaseYear(),
        movie.getDuration(),
        movie.getActors().stream().map(ActorEntity::getId).toList(),
        movie.getGenres().stream().map(GenreEntity::getId).toList());
  }
}