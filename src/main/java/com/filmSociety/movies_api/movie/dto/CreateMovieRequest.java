package com.filmSociety.movies_api.movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMovieRequest {
  @NotBlank
  private String title;

  @NotNull
  private Integer releaseYear;

  @NotNull
  private Integer duration;

  public CreateMovieRequest() {
  }

  public CreateMovieRequest(String title, Integer releaseYear, Integer duration) {
    this.title = title;
    this.releaseYear = releaseYear;
    this.duration = duration;
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
}
