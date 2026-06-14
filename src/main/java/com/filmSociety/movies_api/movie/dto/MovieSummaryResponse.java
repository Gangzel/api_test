package com.filmSociety.movies_api.movie.dto;

import java.util.List;

public class MovieSummaryResponse {
  private Long id;
  private Integer releaseYear;
  private Integer duration;
  private List<Long> actors;
  private String title;
  private List<Long> genres;

  public MovieSummaryResponse() {
  }

  public MovieSummaryResponse(String title, Long id, Integer releaseYear, Integer duration, List<Long> actors,
      List<Long> genres) {
    this.id = id;
    this.releaseYear = releaseYear;
    this.duration = duration;
    this.actors = actors;
    this.title = title;
    this.genres = genres;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getReleaseYear() {
    return this.releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
  }

  public Integer getDuration() {
    return this.duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public List<Long> getActors() {
    return this.actors;
  }

  public void setActors(List<Long> actors) {
    this.actors = actors;
  }

  public List<Long> getGenres() {
    return this.genres;
  }

  public void setGenres(List<Long> genres) {
    this.genres = genres;
  }
}
