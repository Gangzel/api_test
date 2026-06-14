package com.filmSociety.movies_api.actor;

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
import com.filmSociety.movies_api.actor.dto.CreateActorRequest;
import com.filmSociety.movies_api.actor.dto.UpdateActorRequest;
import com.filmSociety.movies_api.movie.dto.MovieSummaryResponse;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/actors")
public class ActorsController {
 
  private final ActorsService actorsService;
 
  public ActorsController(ActorsService actorsService) {
    this.actorsService = actorsService;
  }
 
  @GetMapping
  public Page<ActorSummaryResponse> index(
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
 
    if (name != null) {
      return actorsService.getActorsByName(name, page, size);
    }
    return actorsService.getAllActorEntities(page, size);
  }
 
  @GetMapping("/{id}")
  public ActorSummaryResponse getActorById(@PathVariable Long id) {
    return actorsService.getActorById(id);
  }
 
  @PatchMapping("/{id}")
  public ActorSummaryResponse modifyActorById(@PathVariable Long id, @RequestBody UpdateActorRequest actor) {
    return actorsService.modifyActorById(id, actor);
  }
 
  @DeleteMapping("/{id}")
  public String deleteActor(
      @PathVariable Long id,
      @RequestParam(defaultValue = "false") Boolean force) {
    actorsService.deleteActorById(id, force);
    return "Actor deleted";
  }
 
  @GetMapping("/{id}/movies")
  public Page<MovieSummaryResponse> getMoviesOfActor(
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    return actorsService.getMoviesOfActor(id, page, size);
  }
 
  @PostMapping("/{id}/movies/{movieId}")
  public ActorSummaryResponse addMovieToCharacter(@PathVariable Long id, @PathVariable Long movieId) {
    return actorsService.addMovieToCharacter(id, movieId);
  }
 
  @DeleteMapping("/{id}/movies/{movieId}")
  public ActorSummaryResponse removeMovieFromCharacter(@PathVariable Long id, @PathVariable Long movieId) {
    return actorsService.removeMovieFromCharacter(id, movieId);
  }
 
  @PostMapping
  public ActorSummaryResponse createActor(@Valid @RequestBody CreateActorRequest actorEntity) {
    return actorsService.createActorEntity(actorEntity);
  }
}