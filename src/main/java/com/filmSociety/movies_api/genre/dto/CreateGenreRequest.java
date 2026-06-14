package com.filmSociety.movies_api.genre.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateGenreRequest {

  @NotBlank
  private String name;

  public CreateGenreRequest() {
  }

  public CreateGenreRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
