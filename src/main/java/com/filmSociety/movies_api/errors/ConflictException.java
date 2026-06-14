package com.filmSociety.movies_api.errors;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
