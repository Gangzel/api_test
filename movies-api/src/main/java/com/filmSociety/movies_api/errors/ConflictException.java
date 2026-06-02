package com.example.demo.errors;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
