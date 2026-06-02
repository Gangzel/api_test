package com.filmSociety.movies_api.genre;

import java.util.ArrayList;
import java.util.List;

import com.filmSociety.movies_api.movie.MovieEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres")
public class GenreEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "genres")
  @JsonIgnore
  private List<MovieEntity> movies = new ArrayList<>();

  public GenreEntity() {
  }

  public GenreEntity(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<MovieEntity> getMovies() {
    return movies;
  }

  public void setMovies(List<MovieEntity> movies) {
    this.movies = movies;
  }
}
