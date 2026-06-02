package com.filmSociety.movies_api.genre;

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

import com.filmSociety.movies_api.genre.dto.CreateGenreRequest;
import com.filmSociety.movies_api.genre.dto.GenreSummaryResponse;
import com.filmSociety.movies_api.movie.dto.MovieSummaryResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/genres")
public class GenresController {

  private final GenresService genresService;

  public GenresController(GenresService genresService) {
    this.genresService = genresService;
  }

  // Get all genres
  @GetMapping
  public Page<GenreSummaryResponse> index(@RequestParam(defaultValue = "0", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size) {
    return genresService.getAllGenreEntities(page, size);
  }

  // Get genre by id
  @GetMapping("/{id}")
  public GenreSummaryResponse getEntityById(
      @PathVariable Long id) {
    return genresService.getEntityById(id);
  }

  // Modify name of genre
  @PatchMapping("/{id}")
  public GenreSummaryResponse modifyGenre(
      @PathVariable Long id,
      @RequestBody GenreEntity updatedGenre) {
    return genresService.updateGenre(id, updatedGenre);
  }

  // Delete genre
  @DeleteMapping("/{id}")
  public String deleteGenre(@PathVariable Long id,
      @RequestParam(required = false, defaultValue = "false") Boolean force) {
    genresService.deleteGenreEntity(id, force);
    return "Genre deleted";
  }

  // Get movies of a certain genre
  @GetMapping("/{id}/movies")
  public Page<MovieSummaryResponse> getMoviesOfGenre(
      @PathVariable Long id, @RequestParam(defaultValue = "0", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size) {
    return genresService.getMoviesOfGenre(id, page, size);
  }

  // Create new genre
  @PostMapping
  public GenreSummaryResponse createGenre(
      @Valid @RequestBody CreateGenreRequest genreEntity) {
    return genresService.createGenreEntity(genreEntity);
  }
}
