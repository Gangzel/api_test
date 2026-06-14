package com.filmSociety.movies_api.movie;

import java.util.ArrayList;
import java.util.List;

import com.filmSociety.movies_api.actor.ActorEntity;
import com.filmSociety.movies_api.genre.GenreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class MovieEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private Integer releaseYear;

  private Integer duration;

  @ManyToMany
  @JoinTable(
    name = "movie_actors",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  private List<ActorEntity> actors = new ArrayList<>();

  @ManyToMany
  @JoinTable(
    name = "movie_genres",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id")
  )
  private List<GenreEntity> genres = new ArrayList<>();

  public MovieEntity() {
  }

  public MovieEntity(String title, Integer releaseYear, Integer duration) {
    this.title = title;
    this.releaseYear = releaseYear;
    this.duration = duration;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public List<ActorEntity> getActors() {
    return actors;
  }

  public void setActors(List<ActorEntity> actors) {
    this.actors = actors;
  }

  public List<GenreEntity> getGenres() {
    return genres;
  }

  public void setGenres(List<GenreEntity> genres) {
    this.genres = genres;
  }
}
