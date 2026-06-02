package com.filmSociety.movies_api.genre.dto;

public class GenreSummaryResponse {
  private Long id;
  private String name;

  public GenreSummaryResponse() {
  }

  public GenreSummaryResponse(Long id, String name) {
    this.id = id;
    this.name = name;
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
}
