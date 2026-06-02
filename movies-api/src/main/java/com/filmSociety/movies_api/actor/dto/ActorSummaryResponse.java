package com.filmSociety.movies_api.actor.dto;

import java.time.LocalDate;
import java.util.List;

public class ActorSummaryResponse {
  private Long id;
  private String name;
  private List<Long> movies;
  private LocalDate birthDate;

  public ActorSummaryResponse() {
  }

  public ActorSummaryResponse(Long id, String name, List<Long> movies, LocalDate birthDate) {
    this.id = id;
    this.name = name;
    this.movies = movies;
    this.birthDate = birthDate;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Long> getMovies() {
    return this.movies;
  }

  public void setMovies(List<Long> movies) {
    this.movies = movies;
  }

  public LocalDate getBirthDate() {
    return this.birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }
}
