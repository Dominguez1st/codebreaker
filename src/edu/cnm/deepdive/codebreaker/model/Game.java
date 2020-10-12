package edu.cnm.deepdive.codebreaker.model;

import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Compares the user submitted guess to the secret code and give feedback
 * on whether the user was correct or not and also can clear the data to enable
 * the user to play again.
 */
public class Game {

  private static final String BAD_GUESS_PATTERN_FORMAT = "^.*[^%s].*$";
  private static final String ILLEGAL_CHARACTER_MESSAGE =
      "Guess includes invalid characters: required=%s; provided=%s.";
  private static final String IlLEGAL_LENGTH_MESSAGE =
      "Invalid guess length: required=%d; provided=%d.";

  private final Code code;
  private final List<Guess> guesses;
  private final String pool;
  private final int length;
  private final String badGuessPattern;

  /**
   * Generates a code using the length to set how long it is, the pool to determine what
   * characters to use and rng to randomly set the code.
   *
   * @param pool Characters are available.
   * @param length Number of how long the code is.
   * @param rng Used to randomly set the code.
   */
  public Game(String pool, int length, Random rng) {
    code = new Code(pool, length, rng);
    guesses = new LinkedList<>();
    this.pool = pool;
    this.length = length;
    badGuessPattern = String.format(BAD_GUESS_PATTERN_FORMAT, pool);
  }
  /**
   * Returns the secret code.
   */
  public Code getCode() {
    return code;
  }

  /**
   * Returns the guesses.
   */
  public List<Guess> getGuesses() {
    return Collections.unmodifiableList(guesses);
  }

  /**
   * Returns the pool of options.
   */
  public String getPool() {
    return pool;
  }

  /**
   * Returns the length of the code..
   */
  public int getLength() {
    return length;
  }

  /**
   * Returns the number of guesses.
   */
  public int getGuessCount(){
      return guesses.size();
  }

  /**
   * Throws exception if the user guess is contains a character that is not within
   * the pool of acceptable characters or if the user guess is not of the correct length.
   */
  public Guess guess(String text)
      throws IllegalGuessLengthException, IllegalGuessCharacterException{
    if (text.length() != length) {
      throw new IllegalGuessLengthException(String.format(
          IlLEGAL_LENGTH_MESSAGE, length, text.length()));
    }
    if (text.matches(badGuessPattern)) {
      throw new IllegalGuessCharacterException(String.format(
          ILLEGAL_CHARACTER_MESSAGE, pool, text));
    }
    Guess guess = code.new Guess(text);
    guesses.add(guess);
    return guess;
  }

  /**
   * Clears the date so that another game can be started.
   */
  public void restart(){
    guesses.clear();
  }

}
