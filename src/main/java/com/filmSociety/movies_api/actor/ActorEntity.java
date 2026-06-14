package com.filmSociety.movies_api.actor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filmSociety.movies_api.movie.MovieEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "actors")
public class ActorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDate birthDate;

  @ManyToMany(mappedBy = "actors")
  @JsonIgnore
  private List<MovieEntity> movies = new ArrayList<>();

  public ActorEntity() {
  }

  public ActorEntity(String name, LocalDate birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public List<MovieEntity> getMovies() {
    return movies;
  }

  public void setMovies(List<MovieEntity> movies) {
    this.movies = movies;
  }
}
