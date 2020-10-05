package edu.cnm.deepdive.codebreaker.model;

public class IllegalGuessLengthException extends IllegalArgumentException {

  public IllegalGuessLengthException() {
  }

  public IllegalGuessLengthException(String messages) {
    super(messages);
  }

  public IllegalGuessLengthException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalGuessLengthException(Throwable cause) {
    super(cause);
  }
}
