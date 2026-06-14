package com.filmSociety.movies_api.actor.dto;

import java.time.LocalDate;
 
public class UpdateActorRequest {
 
  private String name;
  private LocalDate birthDate;
 
  public UpdateActorRequest() {
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
}
    
