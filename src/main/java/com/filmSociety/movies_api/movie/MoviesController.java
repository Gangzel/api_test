package com.filmSociety.movies_api.movie;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filmSociety.movies_api.actor.dto.ActorSummaryResponse;
import com.filmSociety.movies_api.movie.dto.CreateMovieRequest;
import com.filmSociety.movies_api.movie.dto.MovieSummaryResponse;
import com.filmSociety.movies_api.movie.dto.UpdateMovieRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MoviesController {
 
  private final MovieService movieService;
 
  public MoviesController(MovieService movieService) {
    this.movieService = movieService;
  }
 
  @GetMapping
  public Page<MovieSummaryResponse> index(
      @RequestParam(required = false) Long genre,
      @RequestParam(required = false) Integer year,
      @RequestParam(required = false) Long actor,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
 
    if (genre != null || year != null || actor != null) {
      return movieService.filterByParams(genre, year, actor, page, size);
    }
    return movieService.getAllMovies(page, size);
  }
 
  @GetMapping("/search")
  public Page<MovieSummaryResponse> searchMovies(
      @RequestParam(defaultValue = "") String title,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    return movieService.searchMoviesByTitle(title, page, size);
  }
 
  @GetMapping("/{id}")
  public MovieSummaryResponse getMovieById(@PathVariable Long id) {
    return movieService.getMovieById(id);
  }
 
  @PatchMapping("/{id}")
  public MovieSummaryResponse updateMovieDetails(@PathVariable Long id, @RequestBody UpdateMovieRequest movie) {
    return movieService.updateMovieDetails(id, movie);
  }
 
  @PostMapping
  public MovieSummaryResponse createMovie(@Valid @RequestBody CreateMovieRequest movie) {
    return movieService.createNewMovie(movie);
  }
 
  @DeleteMapping("/{id}")
  public String deleteMovie(@PathVariable Long id) {
    movieService.deleteMovieById(id);
    return "Movie deleted";
  }
 
  @GetMapping("/{id}/actors")
  public Page<ActorSummaryResponse> getAllActorsOfMovie(
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    return movieService.getAllActorsOfMovieById(id, page, size);
  }
 
  @PostMapping("/{id}/actors/{actorId}")
  public MovieSummaryResponse addActorToMovie(@PathVariable Long id, @PathVariable Long actorId) {
    return movieService.addActorToMovie(id, actorId);
  }
 
  @DeleteMapping("/{id}/actors/{actorId}")
  public String removeActorFromMovie(@PathVariable Long id, @PathVariable Long actorId) {
    movieService.removeActorFromMovie(id, actorId);
    return "Actor removed from movie";
  }
 
  @PostMapping("/{id}/genres/{genreId}")
  public MovieSummaryResponse addGenreToMovie(@PathVariable Long id, @PathVariable Long genreId) {
    return movieService.addGenreToMovie(id, genreId);
  }
 
  @DeleteMapping("/{id}/genres/{genreId}")
  public String removeGenreFromMovie(@PathVariable Long id, @PathVariable Long genreId) {
    movieService.removeGenreFromMovie(id, genreId);
    return "Genre removed from movie";
  }
}