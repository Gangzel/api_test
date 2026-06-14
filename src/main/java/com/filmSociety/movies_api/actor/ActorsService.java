package com.filmSociety.movies_api.actor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import com.filmSociety.movies_api.actor.dto.ActorSummaryResponse;
import com.filmSociety.movies_api.actor.dto.CreateActorRequest;
import com.filmSociety.movies_api.actor.dto.UpdateActorRequest;
import com.filmSociety.movies_api.genre.GenreEntity;
import com.filmSociety.movies_api.movie.MovieEntity;
import com.filmSociety.movies_api.movie.MovieRepository;
import com.filmSociety.movies_api.movie.dto.MovieSummaryResponse;
 
@Service
public class ActorsService {
 
  private final ActorsRepository actorsRepository;
  private final MovieRepository movieRepository;
 
  public ActorsService(ActorsRepository actorsRepository, MovieRepository movieRepository) {
    this.actorsRepository = actorsRepository;
    this.movieRepository = movieRepository;
  }
 
  public Page<ActorSummaryResponse> getAllActorEntities(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return actorsRepository.findAll(pageable).map(this::toResponse);
  }
 
  public ActorSummaryResponse getActorById(Long id) {
    return toResponse(findActorOrThrow(id));
  }
 
  public Page<ActorSummaryResponse> getActorsByName(String name, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return actorsRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toResponse);
  }
 
  public ActorSummaryResponse modifyActorById(Long id, UpdateActorRequest update) {
    ActorEntity actor = findActorOrThrow(id);
 
    if (update.getName() != null) actor.setName(update.getName());
    if (update.getBirthDate() != null) actor.setBirthDate(update.getBirthDate());
 
    return toResponse(actorsRepository.save(actor));
  }
 
  public ActorSummaryResponse createActorEntity(CreateActorRequest request) {
    ActorEntity actor = new ActorEntity();
    actor.setName(request.getName());
    actor.setBirthDate(request.getBirthDate());
    return toResponse(actorsRepository.save(actor));
  }
 
  public void deleteActorById(Long id, Boolean force) {
    ActorEntity actor = findActorOrThrow(id);
 
    if (force) {
      actorsRepository.delete(actor);
    } else {
      long moviesCount = movieRepository.countByActors_Id(id);
      if (moviesCount > 0) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Actor is used by " + moviesCount + " movies");
      }
      actorsRepository.delete(actor);
    }
  }
 
  public Page<MovieSummaryResponse> getMoviesOfActor(Long id, Integer page, Integer size) {
    findActorOrThrow(id);
    Pageable pageable = PageRequest.of(page, size);
    return movieRepository.findByActors_Id(id, pageable).map(movie -> new MovieSummaryResponse(
        movie.getTitle(), movie.getId(), movie.getReleaseYear(), movie.getDuration(),
        movie.getActors().stream().map(ActorEntity::getId).toList(),
        movie.getGenres().stream().map(GenreEntity::getId).toList()));
  }
 
  public ActorSummaryResponse addMovieToCharacter(Long id, Long movieId) {
    ActorEntity actor = findActorOrThrow(id);
    MovieEntity movie = movieRepository.findById(movieId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
 
    // Movie is the owning side, so we modify the movie's actors list
    movie.getActors().add(actor);
    movieRepository.save(movie);
 
    return toResponse(findActorOrThrow(id));
  }
 
  public ActorSummaryResponse removeMovieFromCharacter(Long id, Long movieId) {
    findActorOrThrow(id);
    MovieEntity movie = movieRepository.findById(movieId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
 
    movie.getActors().removeIf(a -> a.getId().equals(id));
    movieRepository.save(movie);
 
    return toResponse(findActorOrThrow(id));
  }
 
  // --- Helpers ---
 
  private ActorEntity findActorOrThrow(Long id) {
    return actorsRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));
  }
 
  private ActorSummaryResponse toResponse(ActorEntity actor) {
    return new ActorSummaryResponse(
        actor.getId(),
        actor.getName(),
        actor.getMovies().stream().map(MovieEntity::getId).toList(),
        actor.getBirthDate());
  }
}